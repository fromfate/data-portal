package com.asiainfo.monitor.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-02
 * @Description: 健康监控--应用基本信息实体
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sjzt_monitor_app_health")
public class AppHealthPO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 服务名
     */
    private String appName;

    /**
     * 服务IP
     */
    private String appIp;

    /**
     * 服务端口
     */
    private String appPort;

    /**
     * 上报频率，以秒为单位
     */
    private Integer reportFrequency;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

}
