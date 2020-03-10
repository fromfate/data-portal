package com.asiainfo.monitor.model.vo;

import com.asiainfo.monitor.model.po.MonitorClusterPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description:
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "监控模块--集群监控--集群基本信息采集记录")
public class MonitorClusterVo {


    private Integer id;

    @ApiModelProperty("集群名")
    private String clusterName;

    @ApiModelProperty("服务名，如hive,spark")
    private String serviceName;

    @ApiModelProperty("内存_总量")
    private String memoryTotal;

    @ApiModelProperty("内存_使用量")
    private String memoryUse;

    @ApiModelProperty("cpu_总量")
    private String cpuTotal;

    @ApiModelProperty("cpu_使用量")
    private String cpuUse;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public static MonitorClusterVo convertToVo(MonitorClusterPO po) {
        MonitorClusterVo vo = new MonitorClusterVo();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

}
