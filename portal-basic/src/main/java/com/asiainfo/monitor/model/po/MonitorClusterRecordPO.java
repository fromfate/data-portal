package com.asiainfo.monitor.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description: 健康监控--集群信息采集记录
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sjzt_monitor_cluster_record")
public class MonitorClusterRecordPO {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 集群信息ID
     */
    private String clusterId;

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
     * io总量
     */
    private String ioTotal;

    /**
     * io使用量
     */
    private String ioUse;

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
