package com.asiainfo.entity;

import com.alibaba.fastjson.JSON;
import com.asiainfo.dto.GatewayFilterDefinition;
import com.asiainfo.dto.GatewayPredicateDefinition;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gateway_routes",uniqueConstraints = {@UniqueConstraint(columnNames = {"route_id"})})
public class GatewayRoutes {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "路由id不能为空")
    @Size(min=2,max=64,message="路由id长度应在2-64字符之内！")
    @Column(name="route_id",columnDefinition="varchar(64) COMMENT '路由id'")
    private String routeId;

    @NotNull(message = "用户名不能为空")
    @Size(min=2,max=128,message="用户名长度应在2-128字符之内！")
    @Column(name="route_uri",columnDefinition="varchar(128) COMMENT '转发目标uri'")
    private String routeUri;

    @Column(name="route_order",columnDefinition="int(11) COMMENT '路由执行顺序'")
    private Integer routeOrder;

    @Column(name="predicates",columnDefinition="text COMMENT '断言字符串集合'")
    private String predicates;

    @Column(name="filters",columnDefinition="text COMMENT '过滤器字符串集合'")
    private String filters;

    @Column(name="is_ebl",columnDefinition="tinyint(1) COMMENT '是否启用'")
    private Boolean isEbl;

    @Column(name="is_del",columnDefinition="tinyint(1) COMMENT '是否删除'")
    private Boolean isDel;

    @Column(name="create_time",columnDefinition="datetime COMMENT '创建时间'")
    private Date createTime;

    @Column(name="update_time",columnDefinition="datetime COMMENT '创建时间'")
    private Date updateTime;

    /**
     * 获取断言集合
     * @return
     */
    public List<GatewayPredicateDefinition> getPredicateDefinition(){
        if(!StringUtils.isEmpty(this.predicates)){
            return JSON.parseArray(this.predicates , GatewayPredicateDefinition.class);
        }
        return null;
    }

    /**
     * 获取过滤器集合
     * @return
     */
    public List<GatewayFilterDefinition> getFilterDefinition(){
        if(!StringUtils.isEmpty(this.filters)){
            return JSON.parseArray(this.filters , GatewayFilterDefinition.class);
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    public String getRouteUri() {
        return routeUri;
    }

    public void setRouteUri(String routeUri) {
        this.routeUri = routeUri == null ? null : routeUri.trim();
    }

    public Integer getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(Integer routeOrder) {
        this.routeOrder = routeOrder;
    }

    public Boolean getIsEbl() {
        return isEbl;
    }

    public void setIsEbl(Boolean isEbl) {
        this.isEbl = isEbl;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates == null ? null : predicates.trim();
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters == null ? null : filters.trim();
    }


    @Override
    public String toString() {
        return "GatewayRoutes{" +
                "id=" + id +
                ", routeId='" + routeId + '\'' +
                ", routeUri='" + routeUri + '\'' +
                ", routeOrder=" + routeOrder +
                ", isEbl=" + isEbl +
                ", isDel=" + isDel +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", predicates='" + predicates + '\'' +
                ", filters='" + filters + '\'' +
                '}';
    }
}