package com.asiainfo.monitor.controller;

import com.asiainfo.monitor.command.ListAppHealthCommand;
import com.asiainfo.monitor.command.ReportAppHealthCommand;
import com.asiainfo.monitor.common.PageInfo;
import com.asiainfo.monitor.common.Result;
import com.asiainfo.monitor.model.vo.AppHealthHistoryVo;
import com.asiainfo.monitor.model.vo.AppHealthListVo;
import com.asiainfo.monitor.service.AppHealthService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: LiuJH
 * @Date: 2020-02-28
 * @Description:
 */
@RequestMapping("/appHealth")
@RestController
@Api(tags = "监控模块--服务健康监控")
public class AppHealthController {

    @Autowired
    private AppHealthService appHealthService;

    /**
     * 应用主动上报基本信息接口
     *
     * @param command
     * @return
     */
    @PostMapping("/report")
    @ApiOperation("上报服务健康状态")
    public Result report(ReportAppHealthCommand command) {

        command.validate();
        appHealthService.report(command.createAppHealthPO());

        return Result.success();
    }


    /**
     * 获取应用状态列表接口
     *
     * @param command
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取服务列表")
    public Result<PageInfo<AppHealthListVo>> list(ListAppHealthCommand command) {
        command.checkOrChangePageParam();
        return Result.success(appHealthService.list(command));
    }

    @GetMapping("/history/{id}")
    @ApiOperation("获取服务历史健康状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id"),
            @ApiImplicitParam(dataType = "string", name = "date", value = "月份 yyyy-MM")
    })
    public Result<List<AppHealthHistoryVo>> history(@PathVariable Integer id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM") Date date) {

        return Result.success(appHealthService.history(id, date));
    }
}
