package com.asiainfo.monitor.dao;

import com.asiainfo.monitor.model.po.MonitorTenantResourcePO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description:
 */
public interface MonitorTenantResourceDao extends PagingAndSortingRepository<MonitorTenantResourcePO, Integer> {
    /**
     *
     * @param tenantId
     * @param pageable
     * @return
     */
    Page<MonitorTenantResourcePO> findByTenantId(String tenantId, Pageable pageable);
}
