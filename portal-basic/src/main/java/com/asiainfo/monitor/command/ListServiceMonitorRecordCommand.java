package com.asiainfo.monitor.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: LiuJH
 * @Date: 2020-03-05
 * @Description:
 */
@Data
@ApiModel(description = "监控模块--集群监控--获取集群服务监控记录")
public class ListServiceMonitorRecordCommand {

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

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("开始日期 yyyy-MM-dd 格式")
    private Date startDate;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("结束日期 yyyy-MM-dd 格式")
    private Date endDate;


    public void checkOrChangePageParam() {
        if (Objects.isNull(currentPage) || currentPage < 0) {
            currentPage = 1;
        }
        if (Objects.isNull(size) || size <= 0) {
            size = 10;
        }
    }

    public void validate() {
        if (Objects.isNull(startDate) || Objects.isNull(endDate) || endDate.before(startDate)) {
            throw new IllegalArgumentException();
        }
    }
}
