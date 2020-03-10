package com.asiainfo.monitor.dao;

import com.asiainfo.monitor.model.po.AppHealthLogPO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author: LiuJH
 * @Date: 2020-03-03
 * @Description:
 */
public interface AppHealthLogDao extends PagingAndSortingRepository<AppHealthLogPO, Integer> {
}
