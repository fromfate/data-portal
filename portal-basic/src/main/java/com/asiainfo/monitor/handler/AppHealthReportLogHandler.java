package com.asiainfo.monitor.handler;

import com.asiainfo.monitor.dao.AppHealthLogDao;
import com.asiainfo.monitor.model.po.AppHealthLogPO;
import com.asiainfo.monitor.model.po.AppHealthRecordPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-03
 * @Description:
 */
@Component
@Slf4j
public class AppHealthReportLogHandler {

    @Autowired
    private AppHealthLogDao appHealthLogDao;

    /**
     * 保存应用上报日志，当前状态，当前上报时间，当前上报频率
     *
     * @param appHealthRecord
     * @param date
     * @param status
     */
    @Async
    public void asyncSaveAppReportLog(AppHealthRecordPO appHealthRecord, Date date, String status) {
        AppHealthLogPO po = new AppHealthLogPO();
        po.setAppBaseId(appHealthRecord.getAppBaseId())
                .setCreateTime(date)
                .setReportFrequency(appHealthRecord.getReportFrequency())
                .setStatus(status);
        appHealthLogDao.save(po);
    }


}
