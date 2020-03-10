package com.asiainfo.monitor.service;

import com.asiainfo.monitor.command.ListServiceMonitorRecordCommand;
import com.asiainfo.monitor.common.PageInfo;
import com.asiainfo.monitor.model.vo.MonitorClusterRecordVo;
import com.asiainfo.monitor.model.vo.MonitorClusterVo;

import java.util.List;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description:
 */
public interface ClusterMonitorService {

    /**
     * 获取所有服务信息
     *
     * @return
     */
    List<MonitorClusterVo> listAllService();

    /**
     * 获取服务监控记录
     *
     * @param clusterId
     * @param command
     * @return
     */
    PageInfo<MonitorClusterRecordVo> listServiceMonitorRecord(String clusterId, ListServiceMonitorRecordCommand command);
}
