package com.asiainfo.dao;

import com.asiainfo.entity.UserOperaterLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserOperaterLogsServiceDao extends PagingAndSortingRepository<UserOperaterLogs,Integer> {

    Page<UserOperaterLogs> findAll(Specification<UserOperaterLogs> spec, Pageable pageable);
}
