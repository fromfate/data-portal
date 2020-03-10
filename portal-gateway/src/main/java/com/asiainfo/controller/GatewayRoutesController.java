package com.asiainfo.controller;

import com.asiainfo.dto.GatewayRouteDefinition;
import com.asiainfo.dto.RestResponse;
import com.asiainfo.entity.GatewayRoutes;
import com.asiainfo.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateway-routes")
public class GatewayRoutesController {

    @Autowired
    private RoutesService routesService;

    /**
     * 获取所有动态路由信息
     * @return
     */
    @RequestMapping("/routes")
    public RestResponse getRouteDefinitions(){
        return new RestResponse(routesService.getRouteDefinitions(),"查询成功");
    }



    //添加路由信息
    @PostMapping("/add")
    public RestResponse add(@RequestBody GatewayRouteDefinition gatewayRouteDefinition){
        GatewayRoutes result = routesService.add(gatewayRouteDefinition);
        return new RestResponse(result,"添加成功");
    }

    //修改路由信息
    @PutMapping("/edit")
    public RestResponse edit(@RequestBody GatewayRouteDefinition gatewayRouteDefinition){
        return routesService.update(gatewayRouteDefinition) > 0 ? new RestResponse("修改成功") : new RestResponse("修改失败");
    }


    @DeleteMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Long id){
        return routesService.delete(id , true) > 0 ? new RestResponse("删除成功") : new RestResponse("删除失败");
    }
}
