package com.asiainfo.service;

import com.asiainfo.dto.PageDto;
import com.asiainfo.entity.BrchInfo;

import java.util.List;

public interface BrchService {

    BrchInfo addBrchInfo(BrchInfo brchInfo);

    void delBrchInfo(int brchId);

    int updateBrchInfo(BrchInfo brchInfo);

    int updateForUse(BrchInfo brchInfo);

    BrchInfo queryById(int brchId);

    PageDto queryBrchPageList(Integer pageNum, Integer pageSize, String brchname);

    List<BrchInfo> queryBrchList(String brchname);
}
