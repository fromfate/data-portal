package com.asiainfo.monitor.controller;

import com.asiainfo.monitor.command.ListServiceMonitorRecordCommand;
import com.asiainfo.monitor.common.PageInfo;
import com.asiainfo.monitor.common.Result;
import com.asiainfo.monitor.model.vo.MonitorClusterRecordVo;
import com.asiainfo.monitor.model.vo.MonitorClusterVo;
import com.asiainfo.monitor.service.ClusterMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description:
 */
@RequestMapping("/cluster")
@RestController
@Api(tags = "监控模块--集群监控")
public class ClusterMonitorController {


    @Autowired
    private ClusterMonitorService clusterMonitorService;


    /**
     * 获取所有服务
     *
     * @return
     */
    @GetMapping("listAllService")
    @ApiOperation("获取所有集群服务")
    public Result<List<MonitorClusterVo>> listAllService() {
        return Result.success(clusterMonitorService.listAllService());
    }

    /**
     * 获取服务监控记录
     *
     * @return
     */
    @GetMapping("listServiceMonitorRecord/{clusterId}")
    @ApiOperation("获取集群服务监控信息")
    public Result<PageInfo<MonitorClusterRecordVo>> listServiceMonitorRecord(@PathVariable String clusterId, ListServiceMonitorRecordCommand command) {
        command.checkOrChangePageParam();
        command.validate();
        return Result.success(clusterMonitorService.listServiceMonitorRecord(clusterId, command));
    }

}
