package com.asiainfo.monitor.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: LiuJH
 * @Date: 2020-03-03
 * @Description:
 */
@Data
@ApiModel("监控模块--服务健康监控--服务健康监控历史记录")
public class AppHealthHistoryVo {

    @ApiModelProperty("日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    @ApiModelProperty("状态 1. 正常 2. 告警 3. 故障")
    private String status;

}
