package com.asiainfo.monitor.controller;

import com.asiainfo.monitor.command.ListTenantResourceCommand;
import com.asiainfo.monitor.common.PageInfo;
import com.asiainfo.monitor.common.Result;
import com.asiainfo.monitor.model.vo.MonitorTenantResourceVo;
import com.asiainfo.monitor.model.vo.MonitorTenantVo;
import com.asiainfo.monitor.service.TenantMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description:
 */
@RequestMapping("/tenant")
@RestController
@Api(tags = "监控模块--租户监控")
public class TenantMonitorController {


    @Autowired
    private TenantMonitorService tenantMonitorService;


    /**
     * 获取租户资源使用信息
     * @param tenantId
     * @return
     */
    @GetMapping("/getMonitorTenant/{tenantId}")
    @ApiOperation("获取指定租户资源使用情况")
    public Result<MonitorTenantVo> getMonitorTenant(@PathVariable String tenantId) {
        return Result.success(tenantMonitorService.getMonitorTenant(tenantId));
    }


    /**
     * 获取租户数据源使用信息
     * @param tenantId
     * @param command
     * @return
     */
    @GetMapping("/listTenantResource/{tenantId}")
    @ApiOperation("获取指定租户数据源使用情况列表")
    public Result<PageInfo<MonitorTenantResourceVo>> listTenantResource(@PathVariable String tenantId, ListTenantResourceCommand command) {
        command.checkOrChangePageParam();
        return Result.success(tenantMonitorService.listTenantResource(tenantId, command));
    }


}
