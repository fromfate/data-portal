package com.asiainfo.monitor.dao;

import com.asiainfo.monitor.model.po.AppHealthRecordPO;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author: LiuJH
 * @Date: 2020-03-02
 * @Description:
 */
public interface AppHealthRecordDao extends PagingAndSortingRepository<AppHealthRecordPO, Integer> {
    /**
     * 获取当天应用健康记录
     *
     * @param appBaseId
     * @param date
     * @return
     */
    AppHealthRecordPO getByAppBaseIdAndDate(Integer appBaseId, int date);

    /**
     * 根据日期区间条件获取
     *
     * @param appBaseId
     * @param startEpochDay
     * @param endEpochDay
     * @return
     */
    List<AppHealthRecordPO> findByAppBaseIdAndDateBetween(Integer appBaseId, int startEpochDay, int endEpochDay);
}
