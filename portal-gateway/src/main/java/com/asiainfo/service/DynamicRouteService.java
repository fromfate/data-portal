package com.asiainfo.service;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface DynamicRouteService {
    String add(RouteDefinition definition);

    String update(RouteDefinition definition);

    Mono<ResponseEntity<Object>> delete(String id);
}
