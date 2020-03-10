package com.asiainfo.monitor.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * @Author: LiuJH
 * @Date: 2020-03-03
 * @Description:
 */
@Data
@ApiModel(description = "监控模块--服务健康监控--上报信息")
public class ListAppHealthCommand {

    /**
     * 服务名
     */
    @ApiModelProperty("服务名，模糊搜索")
    private String appName;

    /**
     * 状态 1. 正常 2. 告警 3. 故障
     */
    @ApiModelProperty("状态 1. 正常 2. 告警 3. 故障")
    private String status;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Integer currentPage;

    /**
     * 一页多少条
     */
    @ApiModelProperty("一页多少条")
    private Integer size;


    public void checkOrChangePageParam() {
        if (Objects.isNull(currentPage) || currentPage < 0) {
            currentPage = 1;
        }
        if (Objects.isNull(size) || size <= 0) {
            size = 10;
        }
    }

}
