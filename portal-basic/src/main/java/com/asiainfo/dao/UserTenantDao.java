package com.asiainfo.dao;

import com.asiainfo.entity.UserTenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserTenantDao extends PagingAndSortingRepository<UserTenant,Integer> {

    Page<UserTenant> findAll(Specification<UserTenant> spec, Pageable pageable);

}
