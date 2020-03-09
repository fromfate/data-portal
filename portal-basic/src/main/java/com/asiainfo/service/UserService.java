package com.asiainfo.service;

import com.asiainfo.dto.BrchUserAuthorizationDto;
import com.asiainfo.dto.PageDto;
import com.asiainfo.dto.UserAuthorizationDto;
import com.asiainfo.dto.UserDto;
import com.asiainfo.entity.User;
import com.asiainfo.vo.UserRoleAndTenantVo;

import java.util.List;

public interface UserService {

    User addUser(UserDto userDto);

    void delUser(int userId);

    int updateUser(User user);

    int updateForUse(User user);

    User queryById(int userId);

    List<User> queryUserList(String userName);

    List<User> queryUserListForImport(String userIds);

    PageDto queryUserPageList(Integer pageNum, Integer pageSize, String userName);

    void addUserAuthorization(UserAuthorizationDto userAuthorizationDto);

    void batchUserAuthorization(BrchUserAuthorizationDto brchUserAuthorizationDto);

    UserRoleAndTenantVo queryUserAuthorizationed(Integer userId);
}
