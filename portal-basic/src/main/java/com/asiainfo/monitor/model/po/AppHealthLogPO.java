package com.asiainfo.monitor.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-03
 * @Description:
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sjzt_monitor_app_health_log")
public class AppHealthLogPO {

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
     * 状态1 正常 2 警告 3 故障
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

}
