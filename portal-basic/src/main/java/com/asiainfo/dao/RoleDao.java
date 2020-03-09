package com.asiainfo.dao;

import com.asiainfo.entity.Role;
import com.asiainfo.entity.TenantUserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleDao extends PagingAndSortingRepository<Role,Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Role r set " +
            "r.roleName = CASE WHEN :#{#role.roleName} IS NULL THEN r.roleName ELSE :#{#role.roleName} END ," +
            "r.roleCode = CASE WHEN :#{#role.roleCode} IS NULL THEN r.roleCode ELSE :#{#role.roleCode} END ," +
            "r.roleDescription = CASE WHEN :#{#role.roleDescription} IS NULL THEN r.roleDescription ELSE :#{#role.roleDescription} END ," +
            "r.platform = CASE WHEN :#{#role.platform} IS NULL THEN r.platform ELSE :#{#role.platform} END ," +
            "r.roleValid = CASE WHEN :#{#role.roleValid} IS NULL THEN r.roleValid ELSE :#{#role.roleValid} END " +
            "where r.roleId = :#{#role.roleId}")
    int updateRole(@Param("role") Role role);

    Page<Role> findAll(Specification<Role> spec, Pageable pageable);

    @Modifying
    @Query(value="update Role set roleValid = :valid where roleId in (:ids)")
    int batchUpdate(@Param("ids") List ids,@Param("valid") Boolean valid);

    @Modifying
    @Query(value="delete from Role where roleId in (:ids)")
    int batchDelete(@Param("ids") List ids);


}
