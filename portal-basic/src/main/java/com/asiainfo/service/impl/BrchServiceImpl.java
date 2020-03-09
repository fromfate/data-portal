package com.asiainfo.service.impl;

import com.asiainfo.dao.BrchDao;
import com.asiainfo.dto.PageDto;
import com.asiainfo.entity.BrchInfo;
import com.asiainfo.service.BrchService;
import com.google.common.collect.Lists;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BrchServiceImpl implements BrchService {

    @Autowired
    private BrchDao brchDao;

    @Override
    public BrchInfo addBrchInfo(BrchInfo brchInfo) {
        brchInfo.setRegDts(new Date());
        brchInfo.setModDts(new Date());
        return brchDao.save(brchInfo);
    }

    @Override
    public void delBrchInfo(int userId) {
        brchDao.deleteById(userId);
    }

    @Override
    public int updateBrchInfo(BrchInfo brchInfo) {
        return brchDao.updateBrchInfo(brchInfo);
    }

    @Override
    public int updateForUse(BrchInfo brchInfo) {
        return brchDao.updateForUse(brchInfo);
    }

    @Override
    public BrchInfo queryById(int brchId) {
        Optional<BrchInfo> byId = brchDao.findById(brchId);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    @Override
    public PageDto queryBrchPageList(Integer pageNum, Integer pageSize, String brchName) {
        Sort sort = Sort.by("regDts").descending();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize,sort);
        Specification<BrchInfo> specification = new Specification<BrchInfo>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(brchName != null && !brchName.trim().equals("")){
                    predicate.getExpressions().add(cb.like(root.get("brchName"), "%"+brchName+"%"));
                }
                return predicate;
            }
        };

        Page<BrchInfo> all = brchDao.findAll(specification,pageable);
        PageDto page = new PageDto();
        page.setCurPage(all.getNumber());
        page.setRows(all.getContent());
        page.setTotalRecords(all.getTotalElements());
        page.setSize(all.getSize());
        return page;
    }

    @Override
    public List<BrchInfo> queryBrchList(String brchname) {
        Sort sort = Sort.by("regDts").descending();
        Specification<BrchInfo> specification = new Specification<BrchInfo>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(brchname != null && !brchname.trim().equals("")){
                    predicate.getExpressions().add(cb.like(root.get("brchName"), "%"+brchname+"%"));
                }

                return predicate;
            }
        };
        Iterable<BrchInfo> all = brchDao.findAll(sort);
        return Lists.newArrayList(all);
    }
}
