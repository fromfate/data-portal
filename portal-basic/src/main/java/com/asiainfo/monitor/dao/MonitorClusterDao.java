package com.asiainfo.monitor.dao;

import com.asiainfo.monitor.model.po.MonitorClusterPO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description:
 */
public interface MonitorClusterDao extends PagingAndSortingRepository<MonitorClusterPO, Integer> {
}
