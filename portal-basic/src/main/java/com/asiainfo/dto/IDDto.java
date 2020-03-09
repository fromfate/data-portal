package com.asiainfo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("通用id-DTO")
public class IDDto {
    /**
     * 对应通用id
     */
    @ApiModelProperty(value = "对应通用id" ,example="1")
    private Integer id;
}