package com.asiainfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("用户已授权的角色和租户集合Vo信息")
public class UserRoleAndTenantVo implements Serializable {
    /**
     * 租户集合
     */
    @ApiModelProperty(value = "租户集合")
    private List<UserTenantVo> userTenantVoList;

    /**
     * 角色集合
     */
    @ApiModelProperty(value = "角色集合")
    private List<UserRoleVo> userRoleVoList;
}