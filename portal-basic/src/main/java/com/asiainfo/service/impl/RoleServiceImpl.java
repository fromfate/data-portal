package com.asiainfo.service.impl;

import com.asiainfo.dao.PlatformDao;
import com.asiainfo.dao.RoleDao;
import com.asiainfo.dao.TenantUserRoleDao;
import com.asiainfo.dto.PageDto;
import com.asiainfo.entity.Role;
import com.asiainfo.entity.TenantUserRole;
import com.asiainfo.exception.BadRequestException;
import com.asiainfo.service.PlatformService;
import com.asiainfo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PlatformDao platformDao;

    @Autowired
    private TenantUserRoleDao tenantUserRoleDao;

    @Override
    public Role save(Role role) {
        //platformDao.findById(role.getPlatform().getId());
        return roleDao.save(role);
    }

    @Override
    public int update(Role role) {
        return roleDao.updateRole(role);
    }

    @Override
    public void delete(Long roleId) throws BadRequestException {
        List<TenantUserRole> byRoleId = tenantUserRoleDao.findByRoleId(roleId);
        if(byRoleId.size() > 0){
            throw new BadRequestException("角色下有绑定用户，不能删除");
        }
        roleDao.deleteById(roleId);
    }

    @Override
    public Role getRoleById(long roleId) {
        Optional<Role> byId = roleDao.findById(roleId);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    @Override
    public PageDto queryRoleList(Integer pageNum, Integer pageSize, String roleName) {
        Sort sort = Sort.by("opTime").descending();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize,sort);
        Specification<Role> specification = new Specification<Role>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(roleName != null && !roleName.trim().equals("")){
                    predicate.getExpressions().add(cb.like(root.get("roleName"), roleName+"%"));
                }

                return predicate;
            }
        };

        Page<Role> all = roleDao.findAll(specification,pageable);
        PageDto page = new PageDto();
        page.setCurPage(all.getNumber());
        page.setRows(all.getContent());
        page.setTotalRecords(all.getTotalElements());
        page.setSize(all.getSize());
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdate(List<Long> ids, Boolean valid) {
        return roleDao.batchUpdate(ids, valid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<Long> ids) {
        return roleDao.batchDelete(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int authorize(Long roleId, Long platformId, List<Long> users, List<Long> tenants) {
        List<TenantUserRole> list = new ArrayList<>();
        if(tenants.size() > 0){
            for (Long id:tenants) {
                list.add(new TenantUserRole(id,null,roleId,platformId));
            }
            tenantUserRoleDao.deleteByRoleIdAndUserIdIsNull(roleId);
        }
        if(users.size() > 0){
            for (Long id:users) {
                list.add(new TenantUserRole(null,id,roleId,platformId));
            }
            tenantUserRoleDao.deleteByRoleIdAndTenantIdIsNull(roleId);
        }
        List<TenantUserRole> tenantUserRoles = (List<TenantUserRole>)tenantUserRoleDao.saveAll(list);
        return tenantUserRoles.size();
    }

    @Override
    public List<Long> queryUserByRoleId(Long roleId, String type) {
        if("user".equals(type)){
            return tenantUserRoleDao.findByRoleIdAndTenantIdIsNull(roleId);
        }
        return tenantUserRoleDao.findByRoleIdAndUserIdIsNull(roleId);
    }
}
