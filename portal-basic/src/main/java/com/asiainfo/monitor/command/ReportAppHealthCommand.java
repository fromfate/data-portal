package com.asiainfo.monitor.command;

import com.asiainfo.monitor.model.po.AppHealthPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * @Author: LiuJH
 * @Date: 2020-03-02
 * @Description:
 */
@Data
@ApiModel(description = "监控模块--服务健康监控--上报数据")
public class ReportAppHealthCommand {

    /**
     * 服务名
     */
    @ApiModelProperty("服务名")
    private String appName;

    /**
     * 服务IP
     */
    @ApiModelProperty("服务IP")
    private String appIp;

    /**
     * 服务端口
     */
    @ApiModelProperty("服务端口")
    private String appPort;

    /**
     * 上报频率，以秒为单位
     */
    @ApiModelProperty("上报频率，以秒为单位")
    private Integer reportFrequency;


    public void validate() {
        if (StringUtils.isAnyBlank(appIp, appName, appPort) || Objects.isNull(reportFrequency)) {
            throw new IllegalArgumentException();
        }
    }

    public AppHealthPO createAppHealthPO() {
        AppHealthPO po = new AppHealthPO();
        BeanUtils.copyProperties(this, po);
        return po;
    }

}
