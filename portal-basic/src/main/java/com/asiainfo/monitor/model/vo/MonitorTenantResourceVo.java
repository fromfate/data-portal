package com.asiainfo.monitor.model.vo;

import com.asiainfo.monitor.model.po.MonitorTenantResourcePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description:
 */
@Data
@ApiModel(description = "监控模块--集群监控--租户资源数据源使用记录")
public class MonitorTenantResourceVo {

    @ApiModelProperty("租户id")
    private String tenantId;

    @ApiModelProperty("数据源id")
    private String dbSourceId;

    @ApiModelProperty("数据源名")
    private String dbSourceName;

    @ApiModelProperty("存储空间_总量")
    private String storageTotal;

    @ApiModelProperty("存储空间_使用量")
    private String storageUes;

    @ApiModelProperty("表总量")
    private String tableTotal;

    public static MonitorTenantResourceVo convertToVo(MonitorTenantResourcePO po) {
        MonitorTenantResourceVo vo = new MonitorTenantResourceVo();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }
}
