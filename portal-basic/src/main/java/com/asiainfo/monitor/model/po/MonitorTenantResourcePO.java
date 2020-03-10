package com.asiainfo.monitor.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description:
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sjzt_monitor_tenant_resource")
public class MonitorTenantResourcePO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;


    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 数据源id
     */
    private String dbSourceId;

    /**
     * 数据源名
     */
    private String dbSourceName;

    /**
     * 存储空间_总量
     */
    private String storageTotal;

    /**
     * 存储空间_使用量
     */
    private String storageUes;

    /**
     * 表总量
     */
    private String tableTotal;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 采集时间
     */
    private Date collectTime;
}
