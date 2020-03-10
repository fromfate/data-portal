package com.asiainfo.monitor.common;

import lombok.Data;

/**
 * @Author: LiuJH
 * @Date: 2020-02-27
 * @Description:
 */
@Data
public class Result<T> {

    public final static Integer SUCCESS = 200;

    private final static String SUCCESS_STR = "成功";

    private Integer code;

    private String msg;

    private T data;

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(T data) {
        this.code = SUCCESS;
        this.data = data;
        this.msg = SUCCESS_STR;
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(SUCCESS, msg, data);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static Result success() {
        return new Result(SUCCESS, SUCCESS_STR);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg);
    }


}
