package com.asiainfo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("机构信息")
@Entity
@Table(name = "sjzt_brch")
public class BrchInfo implements Serializable {
    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id" ,example="1")
    @Column(name = "brch_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brchId;

    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称" ,example="机构名称")
    @Column(name = "brch_name")
    private String brchName;

    /**
     * 机构编码
     */
    @ApiModelProperty(value = "机构编码" ,example="机构编码")
    @Column(name = "brch_code")
    private String brchCode;


    /**
     * 上级机构id
     */
    @ApiModelProperty(value = "上级机构id" ,example="1")
    @Column(name = "brch_parent_id")
    private Integer brchParentId;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注" ,example="备注")
    @Column(name = "comment")
    private String comment;

    /**
     * 机构类型
     */
    @ApiModelProperty(value = "机构类型" ,example="0正式1临时")
    @Column(name = "brch_type")
    private String brchType;
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