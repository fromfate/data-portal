package com.asiainfo.service.impl;

import com.asiainfo.dao.UserDao;
import com.asiainfo.dto.PageDto;
import com.asiainfo.entity.User;
import com.asiainfo.service.UserService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Override
    public PageDto queryUserList(Integer pageNum, Integer pageSize, String username) {
        Sort sort = Sort.by("regDts").descending();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize,sort);
        Specification<User> specification = new Specification<User>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(username != null && !username.trim().equals("")){
                    predicate.getExpressions().add(cb.like(root.get("username"), username+"%"));
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
}
