package com.asiainfo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "sjzt_role")
public class Role {

    //role_id	角色id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long roleId;

    //role_name	角色名
    @NotEmpty(message = "角色名不能为空")
    @Column(name="role_name",columnDefinition="varchar(64) COMMENT '角色名'")
    private String roleName;

    //code	编码
    @Column(name="role_code",columnDefinition="varchar(64) COMMENT '编码'")
    private String roleCode;

    //role_description	角色描述
    @Column(name="role_description",columnDefinition="varchar(200) COMMENT '角色描述'")
    private String roleDescription;

    //role_system_id  角色归属平台id
//    @NotNull(message = "所属平台不能为空")
//    @Column(name="role_system_id",columnDefinition="bigint(20) COMMENT '角色归属平台id'")
    @ManyToOne
    @JoinColumn(name="platform_id")
    private Platform platform;

    //role_valid	角色有效性
    @Column(name="role_valid",columnDefinition="boolean COMMENT '是否可用'")
    private Boolean roleValid;

    //op_time	操作时间
    @Column(name="op_time",columnDefinition="datetime COMMENT '操作时间'")
    private Date opTime;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Boolean getRoleValid() {
        return roleValid;
    }

    public void setRoleValid(Boolean roleValid) {
        this.roleValid = roleValid;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
}
