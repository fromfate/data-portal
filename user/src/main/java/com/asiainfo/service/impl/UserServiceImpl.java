package com.asiainfo.service.impl;

import com.asiainfo.dao.UserDao;
import com.asiainfo.entity.User;
import com.asiainfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User addUser(User user) {
        user.setRegDts(new Date());
        return userDao.save(user);
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
    public User queryById(int userId) {
        Optional<User> byId = userDao.findById(userId);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }
}
