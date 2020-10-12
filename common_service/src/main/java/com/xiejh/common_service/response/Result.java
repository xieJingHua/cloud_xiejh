package com.xiejh.common_service.response;

import lombok.Data;

/**
 * 响应结果集
 * @author xiejh
 * @Date 2020/10/10 12:32
 **/
@Data
public class Result<T> {

    private boolean success;

    private int code;

    private String message;

    private T data;

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static <T> Result success(T data) {
        Result result = new Result();
        result.setCode(0);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @param message
     * @return
     */
    public static <T> Result fail(String message) {
        Result result = new Result();
        result.setCode(1);
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}
