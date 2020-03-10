package com.asiainfo.service;

import com.asiainfo.dto.GatewayRouteDefinition;
import com.asiainfo.entity.GatewayRoutes;

import java.util.List;

public interface RoutesService {

    GatewayRoutes add(GatewayRouteDefinition gatewayRouteDefinition);

    int update(GatewayRouteDefinition gatewayRouteDefinition);

    int delete(Long id, boolean isDel);

    int enableById(Long id, boolean isEbl);

    GatewayRoutes getById(Long id);

    /**
     * 查询路由信息
     * @return
     */
    List<GatewayRoutes> getRoutes(GatewayRoutes route);

    /**
     * 返回组装后网关需要的路由信息
     * @return
     */
    List<GatewayRouteDefinition> getRouteDefinitions();
}
