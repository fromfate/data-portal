package com.asiainfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("数据资源信息")
public class AuthorizationVo implements Serializable {
    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id" ,example="1")
    @Column(name = "tenant_id")
    private Integer tenantId;

    /**
     * 资源id
     */
    @ApiModelProperty(value = "资源id" ,example="1")
    @Column(name = "resource_id")
    private int resourceId;
    /**
     * 资源名称
     */
    @ApiModelProperty(value = "资源名称" ,example="mysql.1.1.test")
    @Column(name = "resource_name")
    private String resourceName;
    /**
     * 资源类型
     */
    @ApiModelProperty(value = "资源类型" ,example="mysql")
    @Column(name = "resource_type")
    private String resourceType;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人" ,example="admin")
    @Column(name = "reg_no")
    private String regNo;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "reg_dts")
    private Date regDts;
}