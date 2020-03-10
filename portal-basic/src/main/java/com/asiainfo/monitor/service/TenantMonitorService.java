package com.asiainfo.monitor.service;

import com.asiainfo.monitor.command.ListTenantResourceCommand;
import com.asiainfo.monitor.common.PageInfo;
import com.asiainfo.monitor.model.vo.MonitorTenantResourceVo;
import com.asiainfo.monitor.model.vo.MonitorTenantVo;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description:
 */
public interface TenantMonitorService {

    /**
     * 获取租户监控信息
     *
     * @param tenantId
     * @return
     */
    MonitorTenantVo getMonitorTenant(String tenantId);

    /**
     * 获取租户资源列表
     * @param tenantId
     * @param command
     * @return
     */
    PageInfo<MonitorTenantResourceVo> listTenantResource(String tenantId, ListTenantResourceCommand command);
}
