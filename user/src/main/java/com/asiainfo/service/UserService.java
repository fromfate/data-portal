package com.asiainfo.service;

import com.asiainfo.entity.User;

public interface UserService {

    User addUser(User user);

    void delUser(int userId);

    int updateUser(User user);

    User queryById(int userId);


}
