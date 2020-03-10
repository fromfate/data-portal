package com.asiainfo.monitor.dao;

import com.asiainfo.monitor.model.po.AppHealthPO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author: LiuJH
 * @Date: 2020-02-28
 * @Description:
 */
public interface AppHealthDao extends PagingAndSortingRepository<AppHealthPO, Integer> {

    /**
     * 根据应用当前状态及应用名筛选应用列表
     *
     * @param date
     * @param status
     * @param appName
     * @param epochDay
     * @param alarmDelayPercent
     * @param faultDelayPercent
     * @return
     */
    @Query(value = "SELECT " +
            "            b.*, " +
            "            CASE WHEN c.id IS NULL THEN b.current_status ELSE c.STATUS END AS current_day_status  " +
            "        FROM " +
            "            (SELECT a.id,a.app_name,a.app_ip,a.app_port,  " +
            "                    CASE WHEN (UNIX_TIMESTAMP(:date) - UNIX_TIMESTAMP(a.last_update_time)) <= (:alarmDelayPercent * a.report_frequency / 100 + a.report_frequency) THEN '1'  " +
            "                    WHEN (UNIX_TIMESTAMP(:date) - UNIX_TIMESTAMP(a.last_update_time)) <= (:faultDelayPercent * a.report_frequency / 100 + a.report_frequency) THEN '2' ELSE '3'  " +
            "                    END AS current_status  " +
            "                FROM  " +
            "                    sjzt_monitor_app_health a  " +
            "            ) b  " +
            "        LEFT JOIN sjzt_monitor_app_health_record c ON b.id = c.app_base_id AND c.date = :epochDay  " +
            "        where " +
            "        if(:appName != '',  " +
            "            b.app_name like concat('%',:appName, '%') , " +
            "         1 = 1)  " +
            " and " +
            "        if(:status != '',  " +
            "            b.current_status = :status, 1=1  " +
            "        )  " +
            "   order by b.id limit :start, :size  ", nativeQuery = true)
    List<Object[]> listByAppCurrentStatusAndAppName(@Param("date") Date date, @Param("status") String status, @Param("appName") String appName,
                                                    @Param("epochDay") int epochDay, @Param("alarmDelayPercent") Integer alarmDelayPercent,
                                                    @Param("faultDelayPercent") Integer faultDelayPercent,
                                                    @Param("start") Integer start,
                                                    @Param("size") Integer size);

    /**
     * 根据应用当前状态及应用名筛选应用列表
     *
     * @param date
     * @param status
     * @param appName
     * @param epochDay
     * @param alarmDelayPercent
     * @param faultDelayPercent
     * @return
     */
    @Query(value = "SELECT " +
            "           count(1)         " +
            "        FROM " +
            "            (SELECT a.id,a.app_name,a.app_ip,a.app_port,  " +
            "                    CASE WHEN (UNIX_TIMESTAMP(:date) - UNIX_TIMESTAMP(a.last_update_time)) <= (:alarmDelayPercent * a.report_frequency / 100 + a.report_frequency) THEN '1'  " +
            "                    WHEN (UNIX_TIMESTAMP(:date) - UNIX_TIMESTAMP(a.last_update_time)) <= (:faultDelayPercent * a.report_frequency / 100 + a.report_frequency) THEN '2' ELSE '3'  " +
            "                    END AS current_status  " +
            "                FROM  " +
            "                    sjzt_monitor_app_health a  " +
            "            ) b  " +
            "        LEFT JOIN sjzt_monitor_app_health_record c ON b.id = c.app_base_id AND c.date = :epochDay  " +
            "        where " +
            "        if(:appName != '',  " +
            "            b.app_name like concat('%',:appName, '%') , " +
            "         1 = 1)  " +
            " and " +
            "        if(:status != '',  " +
            "            b.current_status = :status, 1=1  " +
            "        )  " +
            "     ", nativeQuery = true)
    Long countByAppCurrentStatusAndAppName(@Param("date") Date date, @Param("status") String status, @Param("appName") String appName,
                                           @Param("epochDay") int epochDay, @Param("alarmDelayPercent") Integer alarmDelayPercent,
                                           @Param("faultDelayPercent") Integer faultDelayPercent);




    /**
     * 根据名称 IP 端口获取应用基本信息
     *
     * @param appName
     * @param appIp
     * @param appPort
     * @return
     */
    AppHealthPO getByAppNameAndAppIpAndAppPort(String appName, String appIp, String appPort);

    /**
     * 通过ID获取
     * @param id
     * @return
     */
    AppHealthPO getById(Integer id);
}
