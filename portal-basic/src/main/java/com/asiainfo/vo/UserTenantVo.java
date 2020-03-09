package com.asiainfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("租户Vo信息")
public class UserTenantVo implements Serializable {
    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id" ,example="1")
    private Integer tenantId;

    /**
     * 租户名
     */
    @ApiModelProperty(value = "租户名" ,example="租户1")
    private String tenantName;

    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码" ,example="T000001")
    private String tenantCode;
}