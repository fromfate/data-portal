package com.asiainfo.service;

import com.asiainfo.dto.PageDto;
import com.asiainfo.entity.Tenant;
import com.asiainfo.vo.AuthorizationVo;

import java.util.List;

public interface TenantService {

    Tenant addTenant(Tenant tenant);

    void delTenant(int tenantId);

    int updateTenant(Tenant tenant);

    Tenant queryById(int tenantId);

    PageDto queryTenantPageList(Integer pageNum, Integer pageSize, String tenantName);

    List<Tenant> queryTenantList(String tenantName);

    List<AuthorizationVo> queryTenantAuthorizationList(int tenantId);

}
