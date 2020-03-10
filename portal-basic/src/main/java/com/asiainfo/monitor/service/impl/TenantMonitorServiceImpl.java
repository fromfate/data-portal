package com.asiainfo.monitor.service.impl;

import com.asiainfo.monitor.command.ListTenantResourceCommand;
import com.asiainfo.monitor.common.PageInfo;
import com.asiainfo.monitor.dao.MonitorTenantDao;
import com.asiainfo.monitor.dao.MonitorTenantResourceDao;
import com.asiainfo.monitor.model.po.MonitorTenantPO;
import com.asiainfo.monitor.model.po.MonitorTenantResourcePO;
import com.asiainfo.monitor.model.vo.MonitorTenantResourceVo;
import com.asiainfo.monitor.model.vo.MonitorTenantVo;
import com.asiainfo.monitor.service.TenantMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description:
 */
@Service
@Slf4j
public class TenantMonitorServiceImpl implements TenantMonitorService {

    @Autowired
    private MonitorTenantDao monitorTenantDao;

    @Autowired
    private MonitorTenantResourceDao monitorTenantResourceDao;

    @Override
    public MonitorTenantVo getMonitorTenant(String tenantId) {

        MonitorTenantPO monitorTenantPo = monitorTenantDao.getByTenantId(tenantId);

        if (Objects.isNull(monitorTenantPo)) {
            return null;
        }

        return MonitorTenantVo.convertToVo(monitorTenantPo);
    }

    @Override
    public PageInfo<MonitorTenantResourceVo> listTenantResource(String tenantId, ListTenantResourceCommand command) {

        Sort sort = Sort.by("createTime").descending();
        Pageable pageable = PageRequest.of(command.getCurrentPage() - 1, command.getSize(), sort);
        Page<MonitorTenantResourcePO> pos = monitorTenantResourceDao.findByTenantId(tenantId, pageable);

        PageInfo<MonitorTenantResourceVo> pageInfo = new PageInfo<>();
        pageInfo.setSize(command.getSize());
        pageInfo.setCurPage(command.getCurrentPage());
        pageInfo.setTotalRecords(pos.getTotalElements());
        pageInfo.setRows(convertToMonitorTenantResourceVo(pos.getContent()));

        return pageInfo;
    }

    private List<MonitorTenantResourceVo> convertToMonitorTenantResourceVo(List<MonitorTenantResourcePO> pos) {
        return pos.stream().map(MonitorTenantResourceVo::convertToVo).collect(Collectors.toList());
    }
}
