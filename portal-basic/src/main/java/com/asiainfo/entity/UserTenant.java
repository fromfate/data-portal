package com.asiainfo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("用户租户关系信息")
@Entity
@Table(name = "sjzt_user_tenant")
public class UserTenant implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "id" ,example="1")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id" ,example="1")
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id" ,example="1")
    @Column(name = "tenant_id")
    private Integer tenantId;

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