package com.asiainfo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("用户角色关系信息")
@Entity
@Table(name = "sjzt_user_role")
public class UserRole implements Serializable {
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
     * 角色id
     */
    @ApiModelProperty(value = "角色id" ,example="1")
    @Column(name = "role_id")
    private Integer roleId;

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