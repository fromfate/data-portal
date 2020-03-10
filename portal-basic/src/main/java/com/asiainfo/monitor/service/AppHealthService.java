package com.asiainfo.monitor.service;

import com.asiainfo.monitor.command.ListAppHealthCommand;
import com.asiainfo.monitor.common.PageInfo;
import com.asiainfo.monitor.model.po.AppHealthPO;
import com.asiainfo.monitor.model.vo.AppHealthHistoryVo;
import com.asiainfo.monitor.model.vo.AppHealthListVo;

import java.util.Date;
import java.util.List;

/**
 * @Author: LiuJH
 * @Date: 2020-02-28
 * @Description:
 */
public interface AppHealthService {

    /**
     * 上报应用健康状态
     *
     * @param appHealth
     */
    void report(AppHealthPO appHealth);

    /**
     * 获取应用基本信息及健康状态列表
     *
     * @param command
     * @return
     */
    PageInfo<AppHealthListVo> list(ListAppHealthCommand command);

    /**
     * 获取服务健康历史列表
     *
     * @param id
     * @param date
     * @return
     */
    List<AppHealthHistoryVo> history(Integer id, Date date);
}
