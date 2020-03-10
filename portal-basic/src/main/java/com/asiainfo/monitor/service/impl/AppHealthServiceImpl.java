package com.asiainfo.monitor.service.impl;

import com.asiainfo.monitor.command.ListAppHealthCommand;
import com.asiainfo.monitor.common.PageInfo;
import com.asiainfo.monitor.dao.AppHealthDao;
import com.asiainfo.monitor.dao.AppHealthRecordDao;
import com.asiainfo.monitor.handler.AppHealthReportLogHandler;
import com.asiainfo.monitor.model.po.AppHealthPO;
import com.asiainfo.monitor.model.po.AppHealthRecordPO;
import com.asiainfo.monitor.model.vo.AppHealthHistoryVo;
import com.asiainfo.monitor.model.vo.AppHealthListVo;
import com.asiainfo.monitor.service.AppHealthService;
import com.asiainfo.monitor.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: LiuJH
 * @Date: 2020-02-28
 * @Description:
 */
@Service
@Slf4j
public class AppHealthServiceImpl implements AppHealthService {

    /**
     * 告警延迟时间， 如上报频率为60秒， 该值为30， （60 + 60*30/100）秒内上报不会为告警状态
     */
    @Value("${sjzt.app-health.alarmDelayPercent}")
    private Integer alarmDelayPercent;

    /**
     * 故障延迟百分比， 如上报频率为60秒， 该值为50， （60 + 60*50/100）秒内上报不会为故障状态
     */
    @Value("${sjzt.app-health.faultDelayPercent}")
    private Integer faultDelayPercent;

    @Autowired
    private AppHealthDao appHealthDao;

    @Autowired
    private AppHealthRecordDao appHealthRecordDao;

    @Autowired
    private AppHealthReportLogHandler reportLogHandler;


    @Override
    public void report(AppHealthPO appHealth) {
        Date currentDate = new Date();

        appHealth = saveOrUpdateAppHealth(appHealth, currentDate);

        AppHealthRecordPO appHealthRecord = getOrCreateAppHealthRecord(appHealth, currentDate);
        appHealthRecord.setReportFrequency(appHealth.getReportFrequency());

        deduceAndSetAppHealthStatus(appHealthRecord, currentDate);

        saveAppHealthRecord(appHealthRecord);
    }

    /**
     * 如果不存在应用基本信息记录就保存
     *
     * @param appHealth
     * @param date
     * @return
     */
    private AppHealthPO saveOrUpdateAppHealth(AppHealthPO appHealth, Date date) {
        AppHealthPO po = appHealthDao.getByAppNameAndAppIpAndAppPort(appHealth.getAppName(), appHealth.getAppIp(), appHealth.getAppPort());

        if (Objects.nonNull(po)) {
            log.info(" saveOrUpdateAppHealth appBaseId: {}  before: reportFrequency {}, lastUpdateTime{}, " +
                            "now : reportFrequency {}, lastUpdateTime{}",
                    po.getId(), po.getReportFrequency(), po.getLastUpdateTime(),
                    appHealth.getReportFrequency(), appHealth.getLastUpdateTime());

            po.setLastUpdateTime(date);
            po.setReportFrequency(appHealth.getReportFrequency());
            return po;
        } else {
            appHealth.setCreateTime(date);
            appHealth.setLastUpdateTime(date);
            appHealthDao.save(appHealth);
            return appHealth;
        }
    }

    /**
     * 获取或创建当天应用健康记录
     *
     * @param appHealth
     * @param date
     * @return
     */
    private AppHealthRecordPO getOrCreateAppHealthRecord(AppHealthPO appHealth, Date date) {
        int currentEpochDay = (int) DateUtil.getLocalDateTime(date).toLocalDate().toEpochDay();
        Integer appBaseId = appHealth.getId();

        return Optional.ofNullable(appHealthRecordDao.getByAppBaseIdAndDate(appBaseId, currentEpochDay))
                .orElseGet(() -> {
                    AppHealthRecordPO po = new AppHealthRecordPO();
                    po.setAppBaseId(appBaseId)
                            .setDate(currentEpochDay)
                            .setReportFrequency(appHealth.getReportFrequency());

                    // 今天才注册服务，上次上报时间设置为当前时间，之前注册过的服务，上次上报时间为00:00
                    if (date.equals(appHealth.getCreateTime())) {
                        po.setLastUpdateTime(date);
                    } else {
                        po.setLastUpdateTime(DateUtil.getDateByEpochDay(currentEpochDay));
                    }
                    return po;
                });
    }

    /**
     * 推断并设置当天应用健康记录状态
     *
     * @param appHealthRecord
     * @param date
     */
    private void deduceAndSetAppHealthStatus(AppHealthRecordPO appHealthRecord, Date date) {

        // 如果当天状态已变成了故障，不能再变成正常或告警，查看历史日历时需要一天的一个整体状态

        String status = deduceAppHealthStatus(appHealthRecord, date);
        log.info("deduceAndSetAppHealthStatus appBaseId: {} before : status {}, updateTime {}, current status {} ",
                appHealthRecord.getAppBaseId(), appHealthRecord.getStatus(), date, status);

        reportLogHandler.asyncSaveAppReportLog(appHealthRecord, date, status);

        appHealthRecord.setLastUpdateTime(date);

        // 如果已经是故障了就不用改状态了
        if (AppHealthRecordPO.getFaultStatus().equals(appHealthRecord.getStatus())) {
            return;
        }

        // 如果之前状态是健康的随便改状态
        if (AppHealthRecordPO.getUpStatus().equals(appHealthRecord.getStatus()) || Objects.isNull(appHealthRecord.getStatus())) {
            appHealthRecord.setStatus(status);
            return;
        }

        // 如果之前是告警，不能改为健康状态
        if (AppHealthRecordPO.getAlarmStatus().equals(appHealthRecord.getStatus())
                && !AppHealthRecordPO.getUpStatus().equals(status)) {
            appHealthRecord.setStatus(status);
        }
    }

    /**
     * 推断出当前应用状态
     *
     * @param appHealthRecord
     * @param date
     * @return
     */
    private String deduceAppHealthStatus(AppHealthRecordPO appHealthRecord, Date date) {
        // 获取上次更新时间，比对当前时间及频率，获取状态
        long time = appHealthRecord.getLastUpdateTime().getTime();
        long currentTime = date.getTime();

        long intervalTime = currentTime - time;
        long reportFrequency = appHealthRecord.getReportFrequency().longValue();

        if (intervalTime < ((reportFrequency * 1000L) + (alarmDelayPercent * reportFrequency * 1000L / 100))) {
            return AppHealthRecordPO.getUpStatus();
        }

        if (intervalTime < ((reportFrequency * 1000L) + (faultDelayPercent * reportFrequency * 1000L / 100))) {
            return AppHealthRecordPO.getAlarmStatus();
        }

        return AppHealthRecordPO.getFaultStatus();
    }

    /**
     * 保存或更新应用健康记录
     *
     * @param appHealthRecord
     */
    private void saveAppHealthRecord(AppHealthRecordPO appHealthRecord) {
        if (Objects.isNull(appHealthRecord.getId())) {
            appHealthRecordDao.save(appHealthRecord);
        }
    }


    @Override
    public PageInfo<AppHealthListVo> list(ListAppHealthCommand command) {

        int start = (command.getCurrentPage() - 1) * command.getSize();

        int epochDay = (int) LocalDate.now().toEpochDay();
        Date date = new Date();

        PageInfo<AppHealthListVo> pageInfo = new PageInfo<>();
        pageInfo.setCurPage(command.getCurrentPage());
        pageInfo.setSize(command.getSize());

        Long total = appHealthDao.countByAppCurrentStatusAndAppName(date, command.getStatus(), command.getAppName(),
                epochDay, alarmDelayPercent, faultDelayPercent);
        if(Objects.isNull(total) || total.equals(0L)) {
            pageInfo.setTotalRecords(0);
            pageInfo.setRows(Collections.emptyList());
            return pageInfo;
        }

        List<Object[]> voList = appHealthDao.listByAppCurrentStatusAndAppName(date, command.getStatus(), command.getAppName(),
                epochDay, alarmDelayPercent, faultDelayPercent, start, command.getSize());


        pageInfo.setTotalRecords(total);
        pageInfo.setRows(convertToVo(voList));

        return pageInfo;
    }

    private List<AppHealthListVo> convertToVo(List<Object[]> list) {
        return list.stream().map(AppHealthListVo::new).collect(Collectors.toList());
    }

    @Override
    public List<AppHealthHistoryVo> history(Integer id, Date date) {

        AppHealthPO appHealth = appHealthDao.getById(id);
        if (Objects.isNull(appHealth)) {
            return Collections.emptyList();
        }

        // 确定开始时间结束时间
        int createEpochDay = DateUtil.dateToEpochDay(appHealth.getCreateTime());
        int epochDay = DateUtil.dateToEpochDay(date);
        int monthEndEpochDay = DateUtil.getMonthEndEpochDay(epochDay);
        int currentEpochDay = (int) LocalDate.now().toEpochDay();

        int startEpochDay = epochDay > createEpochDay ? epochDay : createEpochDay;
        int endEpochDay = monthEndEpochDay > currentEpochDay ? currentEpochDay : monthEndEpochDay;

        // 开始时间大于结束时间，不用再去查询数据库
        if (startEpochDay > endEpochDay) {
            return Collections.emptyList();
        }

        List<AppHealthRecordPO> appHealthRecords = appHealthRecordDao.findByAppBaseIdAndDateBetween(appHealth.getId(), startEpochDay, endEpochDay);

        return generateAppHealthHistoryVoList(appHealthRecords, startEpochDay, endEpochDay);
    }

    /**
     * 生成应用健康历史vo
     *
     * @param appHealthRecords
     * @param startEpochDay
     * @param endEpochDay
     * @return
     */
    private List<AppHealthHistoryVo> generateAppHealthHistoryVoList(List<AppHealthRecordPO> appHealthRecords, int startEpochDay, int endEpochDay) {
        Map<Integer, AppHealthRecordPO> recordMap = appHealthRecords.stream().collect(Collectors.toMap(AppHealthRecordPO::getDate, v -> v, (v1, v2) -> v1));

        AppHealthRecordPO emptyRecord = new AppHealthRecordPO();
        emptyRecord.setStatus(AppHealthRecordPO.getFaultStatus());

        List<AppHealthHistoryVo> voList = new LinkedList<>();
        while (startEpochDay <= endEpochDay) {
            AppHealthRecordPO record = recordMap.getOrDefault(startEpochDay, emptyRecord);

            AppHealthHistoryVo vo = new AppHealthHistoryVo();
            vo.setStatus(record.getStatus());
            vo.setDate(DateUtil.epochDayToYYYYMMDDString(startEpochDay));

            voList.add(vo);
            startEpochDay++;
        }

        return voList;
    }

}

