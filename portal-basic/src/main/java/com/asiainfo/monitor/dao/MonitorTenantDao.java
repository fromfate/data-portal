package com.asiainfo.monitor.dao;

import com.asiainfo.monitor.model.po.MonitorTenantPO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description:
 */
public interface MonitorTenantDao extends PagingAndSortingRepository<MonitorTenantPO, Integer> {
    /**
     *
     * @param tenantId
     * @return
     */
    MonitorTenantPO getByTenantId(String tenantId);
}
