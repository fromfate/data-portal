package com.asiainfo.monitor.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: LiuJH
 * @Date: 2020-03-06
 * @Description:
 */
@Data
@ApiModel(description = "分页返回结果")
public class PageInfo<T> {

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private int curPage;
    /**
     * 一页多少条
     */
    @ApiModelProperty("一页多少条")
    private int size=20;
    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private long totalRecords=0;


    @ApiModelProperty("结果集")
    private List<T> rows;
}