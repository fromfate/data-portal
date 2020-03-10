package com.asiainfo.monitor.service.impl;

import com.asiainfo.monitor.command.ListServiceMonitorRecordCommand;
import com.asiainfo.monitor.common.PageInfo;
import com.asiainfo.monitor.dao.MonitorClusterDao;
import com.asiainfo.monitor.dao.MonitorClusterRecordDao;
import com.asiainfo.monitor.model.po.MonitorClusterPO;
import com.asiainfo.monitor.model.po.MonitorClusterRecordPO;
import com.asiainfo.monitor.model.vo.MonitorClusterRecordVo;
import com.asiainfo.monitor.model.vo.MonitorClusterVo;
import com.asiainfo.monitor.service.ClusterMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description:
 */
@Service
@Slf4j
public class ClusterMonitorServiceImpl implements ClusterMonitorService {

    @Autowired
    private MonitorClusterDao monitorClusterDao;

    @Autowired
    private MonitorClusterRecordDao monitorClusterRecordDao;


    @Override
    public List<MonitorClusterVo> listAllService() {
        Iterable<MonitorClusterPO> clusterList = monitorClusterDao.findAll();
        return convertToMonitorClusterVo(clusterList);
    }

    @Override
    public PageInfo<MonitorClusterRecordVo> listServiceMonitorRecord(String clusterId, ListServiceMonitorRecordCommand command) {

        Sort sort = Sort.by("createTime").descending();
        Pageable pageable = PageRequest.of(command.getCurrentPage() - 1, command.getSize(), sort);
        Page<MonitorClusterRecordPO> pos = monitorClusterRecordDao.findByClusterIdAndCreateTimeBetween(clusterId, command.getStartDate(), command.getEndDate(), pageable);

        PageInfo<MonitorClusterRecordVo> pageInfo = new PageInfo<>();
        pageInfo.setSize(command.getSize());
        pageInfo.setCurPage(command.getCurrentPage());
        pageInfo.setTotalRecords(pos.getTotalElements());
        pageInfo.setRows(convertToMonitorClusterRecordVo(pos.getContent()));

        return pageInfo;
    }

    /**
     * 转换为Vo
     *
     * @param pos
     * @return
     */
    private List<MonitorClusterRecordVo> convertToMonitorClusterRecordVo(List<MonitorClusterRecordPO> pos) {
        return pos.stream().map(MonitorClusterRecordVo::convertToVo).collect(Collectors.toList());
    }

    /**
     * 转换为Vo
     *
     * @param pos
     * @return
     */
    private List<MonitorClusterVo> convertToMonitorClusterVo(Iterable<MonitorClusterPO> pos) {
        LinkedList<MonitorClusterVo> vos = new LinkedList<>();
        if (Objects.nonNull(pos)) {
            pos.forEach(v -> vos.add(MonitorClusterVo.convertToVo(v)));
        }
        return vos;
    }
}
