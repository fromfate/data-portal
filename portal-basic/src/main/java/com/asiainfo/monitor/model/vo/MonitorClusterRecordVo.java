package com.asiainfo.monitor.model.vo;

import com.asiainfo.monitor.model.po.MonitorClusterRecordPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description: 健康监控--集群信息采集记录
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "监控模块--集群监控--集群信息采集记录")
public class MonitorClusterRecordVo {

    @ApiModelProperty("集群信息ID")
    private String clusterId;

    @ApiModelProperty("内存_总量")
    private String memoryTotal;

    @ApiModelProperty("内存_使用量")
    private String memoryUse;

    @ApiModelProperty("cpu_总量")
    private String cpuTotal;

    @ApiModelProperty("cpu_使用量")
    private String cpuUse;

    @ApiModelProperty("io总量")
    private String ioTotal;

    @ApiModelProperty("io使用量")
    private String ioUse;

    @ApiModelProperty("存储空间_总量")
    private String storageTotal;

    @ApiModelProperty("存储空间_使用量")
    private String storageUse;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public static MonitorClusterRecordVo convertToVo(MonitorClusterRecordPO po) {
        MonitorClusterRecordVo vo = new MonitorClusterRecordVo();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }
}
