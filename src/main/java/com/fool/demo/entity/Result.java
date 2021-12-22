package com.fool.demo.entity;

import java.io.Serializable;

/**
 * @author fool
 * @date 2021/10/18 14:53
 */
public interface Result<T> extends Serializable {
    int SUCCESS = 20000;

    int FAILED = 300;

    int ACCESS_DENIED = 401;

    int AUTH_FAILED = 402;

    int ERROR = 500;


    void setCode(int code);

    int getCode();

    void setMessage(String message);

    String getMessage();

    void setData(T data);

    T getData();
}
