package com.asiainfo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "sjzt_user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userId;

    @NotNull(message = "用户名不能为空")
    @Size(min=2,max=32,message="用户名长度应在2-32字符之内！")
    @Column(name="username",columnDefinition="varchar(50) COMMENT '用户名'")
    String username;


    @Column(name="reg_dts",columnDefinition="datetime COMMENT '创建时间'")
    Date regDts;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRegDts() {
        return regDts;
    }

    public void setRegDts(Date regDts) {
        this.regDts = regDts;
    }
}
