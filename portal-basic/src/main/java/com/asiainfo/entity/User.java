package com.asiainfo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("用户信息")
@Entity
@Table(name = "sjzt_user")
public class User  implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id" ,example="1")
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名称" ,example="zhangsan")
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名" ,example="张三")
    @Column(name = "real_name")
    private String realName;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话" ,example="13699167011")
    @Column(name = "phone")
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码" ,example="密码")
    @Column(name = "password")
    private String password;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码" ,example="110120199009090011")
    @Column(name = "id_cards")
    private String idCards;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱" ,example="1234@qq.com")
    @Column(name = "email")
    private String email;
    /**
     * 所属机构
     */
    @ApiModelProperty(value = "所属机构" ,example="1")
    @Column(name = "brch_id")
    private int brchId;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像" ,example="1")
    @Column(name = "portrait")
    private String portrait;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注" ,example="备注")
    @Column(name = "comment")
    private String comment;
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
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    @Column(name = "last_login_dts")
    private Date lastLoginDts;
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