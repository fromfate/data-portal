package com.asiainfo.monitor.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * @Author: LiuJH
 * @Date: 2020-03-04
 * @Description:
 */
@Data
@ApiModel(description = "监控模块--租户监控--获取租户数据源使用情况")
public class ListTenantResourceCommand {

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
