package com.asiainfo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("租户信息")
@Entity
@Table(name = "sjzt_tenant")
public class Tenant  implements Serializable {
    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id" ,example="1")
    @Column(name = "tenant_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tenantId;

    /**
     * 租户名称
     */
    @ApiModelProperty(value = "租户名称" ,example="租户名称")
    @Column(name = "tenant_name")
    private String tenantName;

    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码" ,example="租户编码")
    @Column(name = "tenant_code")
    private String tenantCode;

    /**
     * 租户标签
     */
    @ApiModelProperty(value = "租户标签" ,example="租户标签")
    @Column(name = "tenant_label")
    private String tenantLabel;

    /**
     * 租户描述
     */
    @ApiModelProperty(value = "租户描述" ,example="这是一个租户描述")
    @Column(name = "tenant_description")
    private String tenantDescription;
    /**
     * 租户归属平台id
     */
    @ApiModelProperty(value = "租户归属平台id" ,example="1")
    @Column(name = "tenant_system_id")
    private Integer tenantSystemId;
    /**
     * 是否使用
     */
    @ApiModelProperty(value = "是否使用" ,example="0正常1异常")
    @Column(name = "use_yn")
    private String useYn;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除" ,example="0正常1删除")
    @Column(name = "del_yn")
    private String delYn;
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
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人" ,example="admin")
    @Column(name = "mod_no")
    private String modNo;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间" ,example="修改时间")
    @Column(name = "mod_dts")
    private Date modDts;
}