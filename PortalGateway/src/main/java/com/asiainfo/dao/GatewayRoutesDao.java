package com.asiainfo.dao;

import com.asiainfo.entity.GatewayRoutes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GatewayRoutesDao extends PagingAndSortingRepository<GatewayRoutes,Long> {

    @Query(nativeQuery = true,value ="select * from gateway_routes gr where gr.is_ebl = TRUE and gr.is_del = FALSE ")
    List<GatewayRoutes> findAll();

    GatewayRoutes findByRouteId(String routeId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update GatewayRoutes r set " +
            "r.routeId = CASE WHEN :#{#route.routeId} IS NULL THEN r.routeId ELSE :#{#route.routeId} END ," +
            "r.routeUri = CASE WHEN :#{#route.routeUri} IS NULL THEN r.routeUri ELSE :#{#route.routeUri} END ," +
            "r.routeOrder = CASE WHEN :#{#route.routeOrder} IS NULL THEN r.routeOrder ELSE :#{#route.routeOrder} END ," +
            "r.predicates = CASE WHEN :#{#route.predicates} IS NULL THEN r.predicates ELSE :#{#route.predicates} END ," +
            "r.filters = CASE WHEN :#{#route.filters} IS NULL THEN r.filters ELSE :#{#route.filters} END ," +
            "r.isEbl = CASE WHEN :#{#route.isEbl} IS NULL THEN r.isEbl ELSE :#{#route.isEbl} END ," +
            "r.isDel = CASE WHEN :#{#route.isDel} IS NULL THEN r.isDel ELSE :#{#route.isDel} END ," +
            "r.updateTime = CASE WHEN :#{#route.updateTime} IS NULL THEN r.updateTime ELSE :#{#route.updateTime} END " +
            "where r.id = :#{#route.id}")
    int update(@Param("route") GatewayRoutes gatewayRoutes);

}
