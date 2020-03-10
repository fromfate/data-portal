package com.asiainfo.monitor.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LiuJH
 * @Date: 2020-03-03
 * @Description:
 */
@Data
@NoArgsConstructor
@ApiModel(description = "监控模块--服务健康监控--服务信息")
public class AppHealthListVo {

    private Integer id;

    @ApiModelProperty("服务名")
    private String appName;

    @ApiModelProperty("服务IP")
    private String appIp;

    @ApiModelProperty("服务端口")
    private String appPort;

    @ApiModelProperty("当前状态")
    private String currentStatus;

    @ApiModelProperty("当天状态")
    private String currentDayStatus;

    public AppHealthListVo(Object[] objects) {
        this.id = (int) objects[0];
        this.appName = (String) objects[1];
        this.appIp = (String) objects[2];
        this.appPort = (String) objects[3];
        this.currentStatus = (String) objects[4];
        this.currentDayStatus = (String) objects[5];
    }
}
