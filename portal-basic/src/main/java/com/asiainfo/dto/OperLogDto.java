package com.asiainfo.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 操作日志记录 oper_log
 * 
 * @author ruoyi
 */
@Data
public class OperLogDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //-------自定义属性-------
    private String operName;//操作人账户
    private String userName;//操作人员姓名
    private String starttime;//开始时间
    private String endtime;//结束时间

}
