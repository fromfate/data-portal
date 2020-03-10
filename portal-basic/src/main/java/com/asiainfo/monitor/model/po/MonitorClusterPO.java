package com.asiainfo.monitor.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description: 健康监控--集群信息记录
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sjzt_monitor_cluster")
public class MonitorClusterPO {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 集群名
     */
    private String clusterName;

    /**
     * 服务名，如hive,spark
     */
    private String serviceName;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 采集时间
     */
    private Date collectTime;
}
