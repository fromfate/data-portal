package com.asiainfo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户信息DTO")
public class UserDto {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id" ,example="1")
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名称" ,example="zhangsan")
    private String userName;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名" ,example="张三")
    private String realName;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话" ,example="13699167011")
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码" ,example="密码")
    private String password;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码" ,example="110120199009090011")
    private String idCards;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱" ,example="1234@qq.com")
    private String email;
    /**
     * 所属机构
     */
    @ApiModelProperty(value = "所属机构" ,example="1")
    private String dept;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像" ,example="1")
    private String portrait;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注" ,example="备注")
    private String comment;
    /**
     * 是否使用
     */
    @ApiModelProperty(value = "是否使用" ,example="0正常1异常")
    private String useYn;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除" ,example="0正常1删除")
    private String delYn;
    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginDts;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人" ,example="admin")
    private String regNo;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date regDts;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人" ,example="admin")
    private String modNo;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间" ,example="修改时间")
    private Date modDts;

    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id" ,example="机构id")
    private int brchId;
}