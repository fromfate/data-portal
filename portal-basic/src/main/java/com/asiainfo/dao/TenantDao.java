package com.asiainfo.dao;

import com.asiainfo.entity.Tenant;
import com.asiainfo.vo.AuthorizationVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface TenantDao extends PagingAndSortingRepository<Tenant,Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Tenant t set " +
            "t.tenantName = CASE WHEN :#{#tenant.brchName} IS NULL THEN t.tenantName ELSE :#{#tenant.tenantName} END " +
            ",t.tenantDescription = CASE WHEN :#{#tenant.tenantDescription} IS NULL THEN t.tenantDescription ELSE :#{#tenant.tenantDescription} END " +
            ",t.tenantSystemId = CASE WHEN :#{#tenant.tenantSystemId} IS NULL THEN t.tenantSystemId ELSE :#{#tenant.tenantSystemId} END " +
            ",t.tenantCode = CASE WHEN :#{#tenant.tenantCode} IS NULL THEN t.tenantCode ELSE :#{#tenant.tenantCode} END " +
            ",t.tenantLabel = CASE WHEN :#{#tenant.tenantLabel} IS NULL THEN t.tenantLabel ELSE :#{#tenant.tenantLabel} END " +
            ",t.delYn = CASE WHEN :#{#tenant.delYn} IS NULL THEN t.delYn ELSE :#{#tenant.delYn} END " +
            ",t.modDts = CASE WHEN :#{#tenant.modDts} IS NULL THEN t.modDts ELSE :#{#tenant.modDts} END " +
            ",t.modNo = CASE WHEN :#{#tenant.modNo} IS NULL THEN t.modNo ELSE :#{#tenant.modNo} END " +
            "where t.id = :#{#tenant.tenantId}")
    int updateTenant(@Param("tenant") Tenant tenant);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Tenant t set " +
            "t.useYn = CASE WHEN :#{#tenant.useYn} IS NULL THEN '0' ELSE :#{#tenant.useYn} END " +
            "where t.id = :#{#tenant.tenantId}")
    int updateUseTenant(@Param("tenant") Tenant tenant);

    Page<Tenant> findAll(Specification<Tenant> spec, Pageable pageable);


    @Query(value = "select * from User u where role in (?1) order by modDts", nativeQuery = true)
    List<AuthorizationVo> queryTenantAuthorizationList(int tenantId);

    @Query(value = "SELECT a.resource_id, a.resource_name, a.resource_type" +
            " from sjzt_tenant_resource b, sjzt_resource a" +
            " where b.tenant_id = a.tenant_id and b.tenant_id = ?1 order by a.resource_name",nativeQuery = true)
    List<Map> queryTenantAuthorizationed(@Param("tenantId") Integer tenantId);
}
