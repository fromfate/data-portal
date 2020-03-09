package com.asiainfo.dao;

import com.asiainfo.entity.TenantUserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TenantUserRoleDao extends PagingAndSortingRepository<TenantUserRole,Long> {

    @Modifying
    @Query(value="delete from TenantUserRole where tenantId is null and roleId =:roleId ")
    int deleteByRoleIdAndTenantIdIsNull(@Param("roleId") Long roleId);


    @Modifying
    @Query(value="delete from TenantUserRole where userId is null and roleId =:roleId ")
    int deleteByRoleIdAndUserIdIsNull(@Param("roleId") Long roleId);

    @Query(nativeQuery = true,value ="select ur.user_id from sjzt_user_role ur where ur.role_id =:roleId and ur.tenant_id is null")
    List<Long> findByRoleIdAndTenantIdIsNull(Long roleId);

    @Query(nativeQuery = true,value ="select ur.tenant_id from sjzt_user_role ur where ur.role_id =:roleId and ur.user_id is null")
    List<Long> findByRoleIdAndUserIdIsNull(Long roleId);

    List<TenantUserRole> findByRoleId(Long roleId);
}
