package com.asiainfo.dao;

import com.asiainfo.entity.Platform;
import com.asiainfo.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PlatformDao extends PagingAndSortingRepository<Platform,Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Platform p set " +
            "p.name = CASE WHEN :#{#platform.name} IS NULL THEN p.name ELSE :#{#platform.name} END ," +
            "p.parentId = CASE WHEN :#{#platform.parentId} IS NULL THEN p.parentId ELSE :#{#platform.parentId} END ," +
            "p.code = CASE WHEN :#{#platform.code} IS NULL THEN p.code ELSE :#{#platform.code} END ," +
            "p.url = CASE WHEN :#{#platform.url} IS NULL THEN p.url ELSE :#{#platform.url} END ," +
            "p.contacts = CASE WHEN :#{#platform.contacts} IS NULL THEN p.contacts ELSE :#{#platform.contacts} END ," +
            "p.contactsNum = CASE WHEN :#{#platform.contactsNum} IS NULL THEN p.contactsNum ELSE :#{#platform.contactsNum} END ," +
            "p.description = CASE WHEN :#{#platform.description} IS NULL THEN p.description ELSE :#{#platform.description} END ," +
            "p.valid = CASE WHEN :#{#platform.valid} IS NULL THEN p.valid ELSE :#{#platform.valid} END " +
            "where p.id = :#{#platform.id}")
    int updatePlatform(@Param("platform") Platform platform);

}
