package com.asiainfo.dao;

import com.asiainfo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface UserDao extends PagingAndSortingRepository<User,Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set " +
            "u.userName = CASE WHEN :#{#user.userName} IS NULL THEN u.userName ELSE :#{#user.userName} END " +
            ",u.realName = CASE WHEN :#{#user.realName} IS NULL THEN u.realName ELSE :#{#user.realName} END " +
            ",u.phone = CASE WHEN :#{#user.phone} IS NULL THEN u.phone ELSE :#{#user.phone} END " +
            ",u.password = CASE WHEN :#{#user.password} IS NULL THEN u.password ELSE :#{#user.password} END " +
            ",u.idCards = CASE WHEN :#{#user.idCards} IS NULL THEN u.idCards ELSE :#{#user.idCards} END " +
            ",u.brchId = CASE WHEN :#{#user.brchId} IS NULL THEN u.brchId ELSE :#{#user.brchId} END " +
            ",u.email = CASE WHEN :#{#user.email} IS NULL THEN u.email ELSE :#{#user.email} END " +
            ",u.portrait = CASE WHEN :#{#user.portrait} IS NULL THEN u.portrait ELSE :#{#user.portrait} END " +
            ",u.delYn = CASE WHEN :#{#user.delYn} IS NULL THEN u.delYn ELSE :#{#user.delYn} END " +
            ",u.comment = CASE WHEN :#{#user.comment} IS NULL THEN u.comment ELSE :#{#user.comment} END " +
            ",u.modNo = CASE WHEN :#{#user.modNo} IS NULL THEN u.modNo ELSE :#{#user.modNo} END " +
            ",u.modDts = CASE WHEN :#{#user.modDts} IS NULL THEN u.modDts ELSE :#{#user.modDts} END " +
            "where u.userId = :#{#user.userId}")
    int updateUser(@Param("user") User user);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set " +
            "u.useYn = CASE WHEN :#{#user.useYn} IS NULL THEN u.useYn ELSE :#{#user.useYn} END " +
            ",u.modNo = CASE WHEN :#{#user.modNo} IS NULL THEN u.modNo ELSE :#{#user.modNo} END " +
            ",u.modDts = CASE WHEN :#{#user.modDts} IS NULL THEN u.modDts ELSE :#{#user.modDts} END " +
            "where u.userId = :#{#user.userId}")
    int updateForUse(@Param("user") User user);

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    @Query(value = "from User u where u.userId in :ids order by u.modDts")
    List<User> queryUserListForImport(@Param("ids") List<String> ids);

    @Query(value = "from User u where u.brchId in :brchIds and u.delYn = 0")
    List<User> queryUserListForBatch(@Param("brchIds") List<Integer> brchIds);

    @Query(value = "SELECT a.role_id, a.role_name,a.role_code" +
            " from sjzt_user_role b, sjzt_role a" +
            " where b.role_id = a.role_id and b.user_id = ?1 order by a.role_name",nativeQuery = true)
    List<Map> queryUserRoleAuthorizationed(@Param("userId") Integer userId);

    @Query(value = "SELECT a.tenant_id, a.tenant_name,a.tenant_code" +
            " from sjzt_user_tenant b, sjzt_tenant a" +
            " where b.tenant_id = a.tenant_id and b.user_id = ?1 order by a.tenant_name",nativeQuery = true)
    List<Map> queryUserTenantAuthorizationed(@Param("userId") Integer userId);
}
