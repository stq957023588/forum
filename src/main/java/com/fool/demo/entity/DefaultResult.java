package com.fool.demo.entity;

import lombok.Data;

/**
 * @author fool
 * @date 2021/10/18 14:53
 */
@Data
public class DefaultResult<T> implements Result<T> {

    private int code;

    private String message;

    private T data;

    public static DefaultResult<Object> success(String message) {
        DefaultResult<Object> result = new DefaultResult<>();
        result.setCode(SUCCESS);
        result.setMessage(message);
        return result;
    }

    public static <O> DefaultResult<O> success(String message, O data) {
        DefaultResult<O> result = new DefaultResult<>();
        result.setCode(SUCCESS);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static DefaultResult<Object> beOffline() {
        DefaultResult<Object> result = new DefaultResult<>();
        result.setCode(AUTH_FAILED);
        result.setMessage("被挤下线");
        return result;
    }

    public static DefaultResult<Object> error(String message) {
        DefaultResult<Object> result = new DefaultResult<>();
        result.setCode(ERROR);
        result.setMessage(message);
        return result;
    }

    public static DefaultResult<Object> accessDenied() {
        DefaultResult<Object> result = new DefaultResult<>();
        result.setCode(ACCESS_DENIED);
        result.setMessage("无权限");
        return result;
    }

    public static DefaultResult<Object> notLoggedIn() {
        DefaultResult<Object> result = new DefaultResult<>();
        result.setCode(ACCESS_DENIED);
        result.setMessage("未登录");
        return result;
    }

    public static DefaultResult<Object> authFailed(String message) {
        DefaultResult<Object> result = new DefaultResult<>();
        result.setCode(AUTH_FAILED);
        result.setMessage(message);
        return result;

    }
}
