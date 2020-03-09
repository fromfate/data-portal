package com.asiainfo.dao;

import com.asiainfo.entity.TenantAuthorization;
import com.asiainfo.vo.AuthorizationVo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TenantAuthorizationDao extends PagingAndSortingRepository<TenantAuthorization,Integer> {
    @Query(value = "select * from User u where role in (?1) order by modDts", nativeQuery = true)
    List<AuthorizationVo> queryTenantAuthorizationList(int tenantId);
}
