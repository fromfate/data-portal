package com.asiainfo.dao;

import com.asiainfo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends PagingAndSortingRepository<User,Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set " +
            "u.username = CASE WHEN :#{#user.username} IS NULL THEN u.username ELSE :#{#user.username} END " +
            "where u.id = :#{#user.id}")
    int updateUser(@Param("user") User user);

    Page<User> findAll(Specification<User> spec, Pageable pageable);

}
