package com.asiainfo.service.impl;

import com.asiainfo.dao.TenantAuthorizationDao;
import com.asiainfo.dto.TenantAuthorizationDto;
import com.asiainfo.entity.TenantAuthorization;
import com.asiainfo.service.TenantAuthorizationService;
import com.asiainfo.vo.AuthorizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TenantAuthorizationServiceImpl implements TenantAuthorizationService {

    @Autowired
    private TenantAuthorizationDao tenantAuthorizationDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addTenantAuthorization(TenantAuthorizationDto tenantAuthorizationDto) {
        if(tenantAuthorizationDto!=null && tenantAuthorizationDto.getResourceIds().size()>0){
            //删除租户资源关系
            TenantAuthorization tenantAuthorizationDel = new TenantAuthorization();
            tenantAuthorizationDel.setTenantId(tenantAuthorizationDto.getTenantId());
            tenantAuthorizationDao.delete(tenantAuthorizationDel);
            //新增租户资源关系
            for (Integer resourceId: tenantAuthorizationDto.getResourceIds()) {
                TenantAuthorization tenantAuthorization = new TenantAuthorization();
                tenantAuthorization.setTenantId(tenantAuthorizationDto.getTenantId());
                tenantAuthorization.setResourceId(resourceId);
                tenantAuthorization.setRegDts(new Date());
                tenantAuthorizationDao.save(tenantAuthorization);
            }
        }
    }

    @Override
    public List<AuthorizationVo> queryTenantAuthorizationList(int tenantId) {
        // TODO
        List<AuthorizationVo> authorizationVoList = tenantAuthorizationDao.queryTenantAuthorizationList(tenantId);
        return authorizationVoList;
    }
}
