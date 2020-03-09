package com.asiainfo.service;

import com.asiainfo.dto.TenantAuthorizationDto;
import com.asiainfo.vo.AuthorizationVo;

import java.util.List;

public interface TenantAuthorizationService {

    void addTenantAuthorization(TenantAuthorizationDto tenantAuthorizationDton);

    List<AuthorizationVo> queryTenantAuthorizationList(int tenantId);

}
