package com.asiainfo.service.impl;

import com.asiainfo.dao.UserOperaterLogsServiceDao;
import com.asiainfo.dto.OperLogDto;
import com.asiainfo.dto.PageDto;
import com.asiainfo.entity.UserOperaterLogs;
import com.asiainfo.service.UserOperaterLogsService;
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

@Service
public class UserOperaterLogsServiceImpl implements UserOperaterLogsService {

    @Autowired
    private UserOperaterLogsServiceDao userOperaterLogsServiceDao;

    public UserOperaterLogs addUserOperaterLogs(UserOperaterLogs userOperaterLogs){
        userOperaterLogs.setOperTime(new Date());
        return userOperaterLogsServiceDao.save(userOperaterLogs);
    }

    public PageDto queryLogsPageList(Integer pageNum, Integer pageSize, OperLogDto operLogDto){
        Sort sort = Sort.by("regDts").descending();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize,sort);
        Specification<UserOperaterLogs> specification = new Specification<UserOperaterLogs>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件
                Predicate predicate = cb.conjunction();
                if(operLogDto.getEndtime() != null){
                    predicate.getExpressions().add(cb.like(root.get("operTime"), "%"+operLogDto.getEndtime()+"%"));
                }
                if(operLogDto.getStarttime() != null){
                    predicate.getExpressions().add(cb.like(root.get("operTime"), "%"+operLogDto.getStarttime()+"%"));
                }
                return predicate;
            }
        };

        Page<UserOperaterLogs> all = userOperaterLogsServiceDao.findAll(specification,pageable);
        PageDto page = new PageDto();
        page.setCurPage(all.getNumber());
        page.setRows(all.getContent());
        page.setTotalRecords(all.getTotalElements());
        page.setSize(all.getSize());
        return page;
    }

}
