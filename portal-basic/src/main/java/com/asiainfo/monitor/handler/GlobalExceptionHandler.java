/*
package com.asiainfo.monitor.handler;

import com.asiainfo.monitor.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

*/
/**
 * @Author: LuShiDe
 * @Date: 2019-12-30
 * @Description: 全局异常处理器
 *//*

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        log.error(" 系统错误 ", e);

        if (e instanceof IllegalArgumentException) {
            return Result.error(1002, "请求参数异常");
        }
        return Result.error(1001, "服务器内部错误");
    }

}
*/
