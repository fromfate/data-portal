package com.asiainfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.asiainfo.dao.UserDao;
import com.asiainfo.dao.UserRoleDao;
import com.asiainfo.dao.UserTenantDao;
import com.asiainfo.dto.BrchUserAuthorizationDto;
import com.asiainfo.dto.PageDto;
import com.asiainfo.dto.UserAuthorizationDto;
import com.asiainfo.dto.UserDto;
import com.asiainfo.entity.User;
import com.asiainfo.entity.UserRole;
import com.asiainfo.entity.UserTenant;
import com.asiainfo.service.UserService;
import com.asiainfo.vo.UserRoleAndTenantVo;
import com.asiainfo.vo.UserRoleVo;
import com.asiainfo.vo.UserTenantVo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private UserTenantDao userTenantDao;

    @Override
    public User addUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        user.setRegNo("");
        user.setRegDts(new Date());
        user.setModNo("");
        user.setModDts(new Date());
        userDao.save(user);
        return user;
    }

    @Override
    public void delUser(int userId) {
        userDao.deleteById(userId);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int updateForUse(User user) {
        return userDao.updateForUse(user);
    }

    @Override
    public User queryById(int userId) {
        Optional<User> byId = userDao.findById(userId);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    @Override
    public PageDto queryUserPageList(Integer pageNum, Integer pageSize, String userName) {
        Sort sort = Sort.by("regDts").descending();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize,sort);
        Specification<User> specification = new Specification<User>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(userName != null && !userName.trim().equals("")){
                    predicate.getExpressions().add(cb.like(root.get("userName"), "%"+userName+"%"));
                }

                return predicate;
            }
        };

        Page<User> all = userDao.findAll(specification,pageable);
        PageDto page = new PageDto();
        page.setCurPage(all.getNumber());
        page.setRows(all.getContent());
        page.setTotalRecords(all.getTotalElements());
        page.setSize(all.getSize());
        return page;
    }

    @Override
    public List<User> queryUserList(String userName) {
        Sort sort = Sort.by("regDts").descending();
        Specification<User> specification = new Specification<User>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(userName != null && !userName.trim().equals("")){
                    predicate.getExpressions().add(cb.like(root.get("userName"), "%"+userName+"%"));
                }

                return predicate;
            }
        };
        Iterable <User> all = userDao.findAll(sort);
        return Lists.newArrayList(all);
    }

    @Override
    public List<User> queryUserListForImport(String userIds) {
        if(StringUtils.isEmpty(userIds)){
            Sort sort = Sort.by("regDts").descending();
            Iterable <User> all = userDao.findAll(sort);
            return Lists.newArrayList(all);
        }else{
            List<String> ids = Arrays.asList(userIds);
            List<User> userList = userDao.queryUserListForImport(ids);
            return userList;
        }
    }

    @Override
    public UserRoleAndTenantVo queryUserAuthorizationed(Integer userId){
        UserRoleAndTenantVo userRoleAndTenantVo = new UserRoleAndTenantVo();
        //查询用户角色集合
        List<Map> userRoleVoMap = userDao.queryUserRoleAuthorizationed(userId);
        String roleStr = JSONObject.toJSONString(userRoleVoMap);
        List<UserRoleVo> userRoleVoList = JSONObject.parseArray(roleStr, UserRoleVo.class);
        userRoleAndTenantVo.setUserRoleVoList(userRoleVoList);
        //查询用户租户集合
        List<Map> userTenantVoMap = userDao.queryUserTenantAuthorizationed(userId);
        String tenantStr = JSONObject.toJSONString(userTenantVoMap);
        List<UserTenantVo> userTenantVoList = JSONObject.parseArray(tenantStr, UserTenantVo.class);
        userRoleAndTenantVo.setUserTenantVoList(userTenantVoList);
        return userRoleAndTenantVo;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addUserAuthorization(UserAuthorizationDto userAuthorizationDto){
        //维护用户角色关系
        if(userAuthorizationDto!=null && userAuthorizationDto.getRuleIds().size()>0){
            //删除角色关系
            UserRole userRole = new UserRole();
            userRole.setUserId(userAuthorizationDto.getUserId());
            userRoleDao.delete(userRole);
            //新增角色关系
            for (Integer roleId: userAuthorizationDto.getRuleIds()) {
                UserRole userRoleAdd = new UserRole();
                userRoleAdd.setUserId(userAuthorizationDto.getUserId());
                userRoleAdd.setRoleId(roleId);
                userRoleAdd.setRegDts(new Date());
                userRoleDao.save(userRoleAdd);
            }
        }
        //维护用户租户关系
        if(userAuthorizationDto!=null && userAuthorizationDto.getTenantIds().size()>0){
            //删除租户关系
            UserTenant userTenant = new UserTenant();
            userTenant.setUserId(userAuthorizationDto.getUserId());
            userTenantDao.delete(userTenant);
            //新增租户关系
            for (Integer tenantId: userAuthorizationDto.getTenantIds()) {
                UserTenant userTenantAdd = new UserTenant();
                userTenantAdd.setUserId(userAuthorizationDto.getUserId());
                userTenantAdd.setTenantId(tenantId);
                userTenantAdd.setRegDts(new Date());
                userTenantDao.save(userTenantAdd);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void batchUserAuthorization(BrchUserAuthorizationDto brchUserAuthorizationDto){
        //查询选择批量机构下所有用户
        if(brchUserAuthorizationDto!=null && brchUserAuthorizationDto.getBrchIds().size()>0){
            List<User> userList =  userDao.queryUserListForBatch(brchUserAuthorizationDto.getBrchIds());
            if(userList!=null && userList.size()>0){
                for (User user: userList) {
                    //维护用户角色关系
                    if(brchUserAuthorizationDto!=null && brchUserAuthorizationDto.getRuleIds().size()>0){
                        //删除角色关系
                        UserRole userRole = new UserRole();
                        userRole.setUserId(user.getUserId());
                        userRoleDao.delete(userRole);
                        //新增角色关系
                        for (Integer roleId: brchUserAuthorizationDto.getRuleIds()) {
                            UserRole userRoleAdd = new UserRole();
                            userRoleAdd.setUserId(user.getUserId());
                            userRoleAdd.setRoleId(roleId);
                            userRoleAdd.setRegDts(new Date());
                            userRoleDao.save(userRoleAdd);
                        }
                    }
                    //维护用户租户关系
                    if(brchUserAuthorizationDto!=null && brchUserAuthorizationDto.getTenantIds().size()>0){
                        //删除租户关系
                        UserTenant userTenant = new UserTenant();
                        userTenant.setUserId(user.getUserId());
                        userTenantDao.delete(userTenant);
                        //新增租户关系
                        for (Integer tenantId: brchUserAuthorizationDto.getTenantIds()) {
                            UserTenant userTenantAdd = new UserTenant();
                            userTenantAdd.setUserId(user.getUserId());
                            userTenantAdd.setTenantId(tenantId);
                            userTenantAdd.setRegDts(new Date());
                            userTenantDao.save(userTenantAdd);
                        }
                    }
                }
            }
        }
    }
}
