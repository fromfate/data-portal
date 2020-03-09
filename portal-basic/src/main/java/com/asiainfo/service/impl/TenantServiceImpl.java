package com.asiainfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.asiainfo.dao.TenantDao;
import com.asiainfo.dto.PageDto;
import com.asiainfo.entity.Tenant;
import com.asiainfo.service.TenantService;
import com.asiainfo.vo.AuthorizationVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantDao tenantDao;

    @Override
    public Tenant addTenant(Tenant tenant) {
        tenant.setRegDts(new Date());
        tenant.setModDts(new Date());
        return tenantDao.save(tenant);
    }

    @Override
    public void delTenant(int tenantId) {
        tenantDao.deleteById(tenantId);
    }

    @Override
    public int updateTenant(Tenant tenant) {
        return tenantDao.updateTenant(tenant);
    }

    @Override
    public Tenant queryById(int tenantId) {
        Optional<Tenant> byId = tenantDao.findById(tenantId);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    @Override
    public PageDto queryTenantPageList(Integer pageNum, Integer pageSize, String tenantame) {
        Sort sort = Sort.by("regDts").descending();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize,sort);
        Specification<Tenant> specification = new Specification<Tenant>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(tenantame != null && !tenantame.trim().equals("")){
                    predicate.getExpressions().add(cb.like(root.get("tenantName"), "%"+tenantame+"%"));
                }

                return predicate;
            }
        };

        Page<Tenant> all = tenantDao.findAll(specification,pageable);
        PageDto page = new PageDto();
        page.setCurPage(all.getNumber());
        page.setRows(all.getContent());
        page.setTotalRecords(all.getTotalElements());
        page.setSize(all.getSize());
        return page;
    }

    @Override
    public List<Tenant> queryTenantList(String tenantame) {
        Sort sort = Sort.by("regDts").descending();
        Specification<Tenant> specification = new Specification<Tenant>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(tenantame != null && !tenantame.trim().equals("")){
                    predicate.getExpressions().add(cb.like(root.get("tenantName"), "%"+tenantame+"%"));
                }

                return predicate;
            }
        };

        Iterable<Tenant> all = tenantDao.findAll(sort);
        return Lists.newArrayList(all);
    }

    @Override
    public List<AuthorizationVo> queryTenantAuthorizationList(int tenantId) {
        //TODO 查询用户租户集合 需要根据资源表修改DAO SQL表名和字段
        List<Map> userTenantVoMap = tenantDao.queryTenantAuthorizationed(tenantId);
        String tenantStr = JSONObject.toJSONString(userTenantVoMap);
        List<AuthorizationVo> authorizationVoList = JSONObject.parseArray(tenantStr, AuthorizationVo.class);
        return authorizationVoList;
    }
}
