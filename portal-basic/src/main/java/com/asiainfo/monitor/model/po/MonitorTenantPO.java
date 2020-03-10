package com.asiainfo.monitor.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description: 租户监控--对应租户资源情况
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sjzt_monitor_tenant")
public class MonitorTenantPO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 内存_总量
     */
    private String memoryTotal;

    /**
     * 内存_使用量
     */
    private String memoryUse;

    /**
     * cpu_总量
     */
    private String cpuTotal;

    /**
     * cpu_使用量
     */
    private String cpuUse;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件个数
     */
    private String fileTotal;

    /**
     * 存储空间_总量
     */
    private String storageTotal;

    /**
     * 存储空间_使用量
     */
    private String storageUse;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 采集时间
     */
    private Date collectTime;


}
