package com.asiainfo.controller;

import com.asiainfo.dto.RestResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认降级处理
 */
@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback")
    public RestResponse defaultfallback(){
        System.out.println("降级操作...");
        return new RestResponse("gateway服务异常");
    }
}
