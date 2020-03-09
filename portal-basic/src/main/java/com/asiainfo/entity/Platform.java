package com.asiainfo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "sjzt_platform")
public class Platform {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    // 平台名称
    @NotEmpty(message = "平台名称不能为空")
    @Column(name="name",columnDefinition="varchar(64) COMMENT '平台名称'")
    private String name;

    // 上级平台
    @Column(name="parent_id",columnDefinition="bigint(20)")
    private Integer parentId;

    // 编码
    @Column(name="code",columnDefinition="varchar(64) COMMENT '编码'")
    private String code;

    // URL
    @NotBlank(message = "URL不能为空")
    @Column(name="url",columnDefinition="varchar(200) COMMENT 'URL'")
    private String url;

    // 联系人
    @NotBlank(message = "联系人不能为空")
    @Column(name="contacts",columnDefinition="varchar(64) COMMENT '编码'")
    private String contacts;

    // 联系人电话
    @NotBlank(message = "联系人电话不能为空")
    @Pattern(regexp = "^\\d{11}$", message = "手机号码格式错误")
    @Column(name="contacts_num",columnDefinition="varchar(11) COMMENT '编码'")
    private String contactsNum;

    // 描述
    @Column(name="description",columnDefinition="varchar(200) COMMENT '描述'")
    private String description;

    // 有效性
    @Column(name="valid",columnDefinition="boolean COMMENT '是否可用'")
    private Boolean valid;

    @Column(name="op_time",columnDefinition="datetime COMMENT '操作时间'")
    private Date opTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsNum() {
        return contactsNum;
    }

    public void setContactsNum(String contactsNum) {
        this.contactsNum = contactsNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
}
