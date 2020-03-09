package com.asiainfo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("批量给组织下所有用户授权资源DTO信息")
public class BrchUserAuthorizationDto implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id" ,example="1")
    private List<Integer> brchIds;

    /**
     * 角色id集合
     */
    @ApiModelProperty(value = "角色id集合" ,example="1")
    private List<Integer> ruleIds;

    /**
     * 租户id集合
     */
    @ApiModelProperty(value = "租户id集合" ,example="1")
    private List<Integer> tenantIds;
}