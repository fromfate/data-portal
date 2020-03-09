package com.asiainfo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("租户授权信息")
@Entity
@Table(name = "sjzt_tenant_resource")
public class TenantAuthorization implements Serializable {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id" ,example="1")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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