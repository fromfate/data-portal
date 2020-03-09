package com.asiainfo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("用户操作日志")
@Entity
@Table(name = "sjzt_user_operater_logs")
public class UserOperaterLogs implements Serializable {
    /** 日志主键 */
    @ApiModelProperty(value = "id" ,example="1")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 操作模块-业务模块 */
    @ApiModelProperty(value = "业务模块" ,example="业务模块")
    @Column(name = "title")
    private String title;

    /** 操作类型 */
    @ApiModelProperty(value = "操作类型" ,example="操作类型")
    @Column(name = "oper_action")
    private String operAction;

    /** 请求方法 */
    @ApiModelProperty(value = "请求方法" ,example="请求方法")
    @Column(name = "oper_method")
    private String operMethod;

    /** 来源渠道 */
    @ApiModelProperty(value = "来源渠道" ,example="来源渠道")
    @Column(name = "channel")
    private String channel;

    /** 操作人员 */
    @ApiModelProperty(value = "操作人员" ,example="操作人员")
    @Column(name = "login_user_id")
    private String loginUserId;

    /** 请求url */
    @ApiModelProperty(value = "请求url" ,example="请求url")
    @Column(name = "oper_url")
    private String operUrl;

    /** 操作地址 */
    @ApiModelProperty(value = "操作地址" ,example="操作地址")
    @Column(name = "oper_ip")
    private String operIp;

    /** 请求参数 */
    @ApiModelProperty(value = "请求参数" ,example="请求参数")
    @Column(name = "oper_param")
    private String operParam;

    /** 状态0正常 1异常 */
    @ApiModelProperty(value = "状态0正常 1异常" ,example="0")
    @Column(name = "status")
    private int status;

    /** 错误消息 */
    @ApiModelProperty(value = "错误消息" ,example="错误消息")
    @Column(name = "error_msg")
    private String errorMsg;

    /** 操作时间 */
    @ApiModelProperty(value = "错误消息" ,example="错误消息")
    @Column(name = "oper_time")
    private Date operTime;
}