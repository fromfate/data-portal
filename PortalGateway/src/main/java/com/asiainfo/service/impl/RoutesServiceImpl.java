package com.asiainfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.asiainfo.dao.GatewayRoutesDao;
import com.asiainfo.dto.GatewayFilterDefinition;
import com.asiainfo.dto.GatewayPredicateDefinition;
import com.asiainfo.dto.GatewayRouteDefinition;
import com.asiainfo.entity.GatewayRoutes;
import com.asiainfo.service.DynamicRouteService;
import com.asiainfo.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoutesServiceImpl implements RoutesService {

    @Autowired
    private GatewayRoutesDao gatewayRoutesDao;

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @Override
    public GatewayRoutes add(GatewayRouteDefinition gatewayRouteDefinition) {

        GatewayRoutes route = transformToGatewayRoutes(gatewayRouteDefinition);
        route.setIsEbl(true);
        route.setIsDel(false);
        route.setCreateTime(new Date());
        route.setUpdateTime(new Date());
        GatewayRoutes save = gatewayRoutesDao.save(route);

        dynamicRouteService.add(assembleRouteDefinition(gatewayRouteDefinition));
        return save;
    }

    @Override
    public int update(GatewayRouteDefinition gatewayRouteDefinition) {
        GatewayRoutes resource = gatewayRoutesDao.findByRouteId(gatewayRouteDefinition.getId());
        if(resource == null){
            return 0;
        }
        GatewayRoutes route = transformToGatewayRoutes(gatewayRouteDefinition);
        route.setId(resource.getId());
        route.setUpdateTime(new Date());
        int updateNum = gatewayRoutesDao.update(route);

        dynamicRouteService.update(assembleRouteDefinition(gatewayRouteDefinition));
        return updateNum;
    }

    @Override
    public int delete(Long id, boolean isDel) {
        Optional<GatewayRoutes> byId = gatewayRoutesDao.findById(id);
        GatewayRoutes routes = new GatewayRoutes();
        if(byId.isPresent()){
            GatewayRoutes result = byId.get();
            routes.setId(result.getId());
            routes.setIsDel(isDel);
            int updateNum = gatewayRoutesDao.update(routes);
            dynamicRouteService.delete(result.getRouteId());
            return updateNum;
        }

        return 0;
    }

    @Override
    public int enableById(Long id, boolean isEbl) {
        Optional<GatewayRoutes> byId = gatewayRoutesDao.findById(id);
        GatewayRoutes routes = new GatewayRoutes();
        if(byId.isPresent()){
            GatewayRoutes result = byId.get();
            routes.setId(result.getId());
            routes.setIsDel(isEbl);
        }

        return gatewayRoutesDao.update(routes);
    }

    @Override
    public GatewayRoutes getById(Long id) {
        Optional<GatewayRoutes> byId = gatewayRoutesDao.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    /**
     * 查询路由信息
     * todo 根据条件查询
     * @return
     */
    @Override
    public List<GatewayRoutes> getRoutes(GatewayRoutes route) {
        return gatewayRoutesDao.findAll();
    }

    /**
     * 返回组装后网关需要的路由信息
     * @return
     */
    @Override
    public List<GatewayRouteDefinition> getRouteDefinitions() {
        List<GatewayRouteDefinition> routeDefinitions = new ArrayList<>();
        GatewayRoutes route = new GatewayRoutes();
        route.setIsDel(false);
        route.setIsEbl(false);
        List<GatewayRoutes> routes = getRoutes(route);
        for(GatewayRoutes gatewayRoute : routes){
            GatewayRouteDefinition routeDefinition = new GatewayRouteDefinition();
            routeDefinition.setId(gatewayRoute.getRouteId());
            routeDefinition.setUri(gatewayRoute.getRouteUri());
            routeDefinition.setFilters(gatewayRoute.getFilterDefinition());
            routeDefinition.setPredicates(gatewayRoute.getPredicateDefinition());
            routeDefinitions.add(routeDefinition);
        }
        return routeDefinitions;
    }

    /**
     * 转化路由对象  GatewayRoutes
     * @param gatewayRouteDefinition
     * @return
     */
    private GatewayRoutes transformToGatewayRoutes(GatewayRouteDefinition gatewayRouteDefinition){
        GatewayRoutes definition = new GatewayRoutes();
        definition.setRouteId(gatewayRouteDefinition.getId());
        definition.setRouteUri(gatewayRouteDefinition.getUri());
        definition.setRouteOrder(gatewayRouteDefinition.getOrder());
        String filters = JSONArray.toJSONString(gatewayRouteDefinition.getFilters());
        String predicates = JSONArray.toJSONString(gatewayRouteDefinition.getPredicates());

        definition.setFilters(filters);
        definition.setPredicates(predicates);

        return definition;
    }

    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(gwdefinition.getId());
        definition.setOrder(gwdefinition.getOrder());

        //设置断言
        List<PredicateDefinition> pdList=new ArrayList<>();
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList=gwdefinition.getPredicates();
        for (GatewayPredicateDefinition gpDefinition: gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);

        //设置过滤器
        List<FilterDefinition> filters = new ArrayList();
        List<GatewayFilterDefinition> gatewayFilters = gwdefinition.getFilters();
        for(GatewayFilterDefinition filterDefinition : gatewayFilters){
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        }
        definition.setFilters(filters);

        URI uri = null;
        if(gwdefinition.getUri().startsWith("http")){
            uri = UriComponentsBuilder.fromHttpUrl(gwdefinition.getUri()).build().toUri();
        }else{
            uri = URI.create(gwdefinition.getUri());
        }
        definition.setUri(uri);
        return definition;
    }
}
