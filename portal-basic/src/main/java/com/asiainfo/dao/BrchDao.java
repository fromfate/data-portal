package com.asiainfo.dao;

import com.asiainfo.entity.BrchInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BrchDao extends PagingAndSortingRepository<BrchInfo,Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update BrchInfo b set " +
            "b.brchName = CASE WHEN :#{#brchInfo.brchName} IS NULL THEN b.brchName ELSE :#{#brchInfo.brchName} END " +
            ",b.brchParentId = CASE WHEN :#{#brchInfo.brchParentId} IS NULL THEN b.brchParentId ELSE :#{#brchInfo.brchParentId} END " +
            ",b.comment = CASE WHEN :#{#brchInfo.comment} IS NULL THEN b.comment ELSE :#{#brchInfo.comment} END " +
            ",b.brchCode = CASE WHEN :#{#brchInfo.brchCode} IS NULL THEN b.brchCode ELSE :#{#brchInfo.brchCode} END " +
            ",b.brchType = CASE WHEN :#{#brchInfo.brchType} IS NULL THEN b.brchType ELSE :#{#brchInfo.brchType} END " +
            ",b.delYn = CASE WHEN :#{#brchInfo.delYn} IS NULL THEN b.delYn ELSE :#{#brchInfo.delYn} END " +
            ",b.modDts = CASE WHEN :#{#brchInfo.modDts} IS NULL THEN b.modDts ELSE :#{#brchInfo.modDts} END " +
            ",b.modNo = CASE WHEN :#{#brchInfo.modNo} IS NULL THEN b.modNo ELSE :#{#brchInfo.modNo} END " +
            "where b.id = :#{#brchInfo.brchId}")
    int updateBrchInfo(@Param("brchInfo") BrchInfo brchInfo);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update BrchInfo b set " +
            "b.useYn = CASE WHEN :#{#brchInfo.useYn} IS NULL THEN '0' ELSE :#{#brchInfo.useYn} END " +
            "where b.id = :#{#brchInfo.brchId}")
    int updateForUse(@Param("brchInfo") BrchInfo brchInfo);

    Page<BrchInfo> findAll(Specification<BrchInfo> spec, Pageable pageable);

}
