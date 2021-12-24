package com.fool.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fool
 * @date 2021/12/24 13:51
 */
@Data
public class ErrorResult<T> implements Result<T> {
    private int code;

    private String message;

    private T data;

    private Date time;

    private String path;

    public ErrorResult(int code, String message, String path) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.time = new Date();
    }

}
