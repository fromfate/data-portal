package com.asiainfo.monitor.model.vo;

import com.asiainfo.monitor.model.po.MonitorTenantPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description:
 */
@Data
@NoArgsConstructor
@ApiModel(description = "监控模块--集群监控--租户资源使用记录")
public class MonitorTenantVo {

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("内存_总量")
    private String memoryTotal;

    @ApiModelProperty("内存_使用量")
    private String memoryUse;

    @ApiModelProperty("cpu_总量")
    private String cpuTotal;

    @ApiModelProperty("cpu_使用量")
    private String cpuUse;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("文件个数")
    private String fileTotal;

    @ApiModelProperty("存储空间_总量")
    private String storageTotal;

    @ApiModelProperty("存储空间_使用量")
    private String storageUse;


    public static MonitorTenantVo convertToVo(MonitorTenantPO po) {
        MonitorTenantVo vo = new MonitorTenantVo();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }
}
