package com.asiainfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("角色Vo信息")
public class UserRoleVo implements Serializable {
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id" ,example="1")
    private Integer roleId;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名" ,example="角色1")
    private String roleName;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码" ,example="R000001")
    private String roleCode;
}