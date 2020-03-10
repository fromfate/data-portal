package com.asiainfo.monitor.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**å
 * @Author: LiuJH
 * @Date: 2020-03-02
 * @Description: 健康监控--应用健康记录详情实体（以天为单位记录）
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sjzt_monitor_app_health_record")
public class AppHealthRecordPO {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 服务基本信息表id，对应AppHealthPO实体中的id
     */
    private Integer appBaseId;

    /**
     * 上报频率，以秒为单位
     */
    private Integer reportFrequency;

    /**
     * 日期，int值表示，从1970年1月1日到当前日期一共多少天，可用LocalDate.now().toEpochDay()获取
     */
    private Integer date;

    /**
     * 状态1 正常 2 警告 3 故障
     */
    private String status;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;


    /**
     * 获取正常状态
     * @return
     */
    public static String getUpStatus() {
        return "1";
    }

    /**
     * 获取告警状态
     * @return
     */
    public static String getAlarmStatus() {
        return "2";
    }

    /**
     * 获取故障状态
     * @return
     */
    public static String getFaultStatus() {
        return "3";
    }

}
