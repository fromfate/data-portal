package com.asiainfo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("租户授权资源DTO信息")
public class TenantAuthorizationDto implements Serializable {
    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id" ,example="1")
    private Integer tenantId;

    /**
     * 资源id
     */
    @ApiModelProperty(value = "资源id" ,example="1")
    private List<Integer> resourceIds;
}