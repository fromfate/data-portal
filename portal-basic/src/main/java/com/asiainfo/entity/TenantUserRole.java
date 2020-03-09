package com.asiainfo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sjzt_user_role",uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id","user_id","role_id"})})
public class TenantUserRole {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //tenant_id	租户id
    @Column(name="tenant_id",columnDefinition="bigint(20)")
    private Long tenantId;

    //user_id	用户id
    @Column(name="user_id",columnDefinition="bigint(20)")
    private Long userId;

    //role_id	角色id
    @Column(name="role_id",columnDefinition="bigint(20)")
    private Long roleId;

    //system_id	所属系统id
    @Column(name="system_id",columnDefinition="bigint(20)")
    private Long systemId;

    //op_time	操作时间
    @Column(name="op_time",columnDefinition="datetime COMMENT '操作时间'")
    private Date opTime;

    public TenantUserRole() {
    }

    public TenantUserRole(Long tenantId, Long userId, Long roleId, Long systemId) {
        this.tenantId = tenantId;
        this.userId = userId;
        this.roleId = roleId;
        this.systemId = systemId;
        this.opTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
}
