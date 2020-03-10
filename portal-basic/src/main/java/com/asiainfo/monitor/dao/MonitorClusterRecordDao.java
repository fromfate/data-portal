package com.asiainfo.monitor.dao;

import com.asiainfo.monitor.model.po.MonitorClusterRecordPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description:
 */
public interface MonitorClusterRecordDao extends PagingAndSortingRepository<MonitorClusterRecordPO, Integer> {
    /**
     *
     * @param clusterId
     * @param startDate
     * @param endDate
     * @param pageable
     * @return
     */
    Page<MonitorClusterRecordPO> findByClusterIdAndCreateTimeBetween(String clusterId, Date startDate, Date endDate, Pageable pageable);
}
