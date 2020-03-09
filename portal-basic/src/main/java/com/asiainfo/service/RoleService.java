package com.asiainfo.service;

import com.asiainfo.dto.PageDto;
import com.asiainfo.entity.Role;
import com.asiainfo.exception.BadRequestException;

import java.util.List;

public interface RoleService {

    Role save(Role role);

    int update(Role role);

    void delete(Long roleId) throws BadRequestException;

    Role getRoleById(long roleId);

    PageDto queryRoleList(Integer pageNum, Integer pageSize, String roleName);

    int batchUpdate(List<Long> ids, Boolean valid);

    int batchDelete(List<Long> ids);

    int authorize(Long roleId, Long platformId, List<Long> users, List<Long> tenants);

    List<Long> queryUserByRoleId(Long roleId, String type);

}
