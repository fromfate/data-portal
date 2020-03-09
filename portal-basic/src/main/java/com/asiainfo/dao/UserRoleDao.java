package com.asiainfo.dao;

import com.asiainfo.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRoleDao extends PagingAndSortingRepository<UserRole,Integer> {

    Page<UserRole> findAll(Specification<UserRole> spec, Pageable pageable);

}
