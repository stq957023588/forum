package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName authority
 */
@Data
public class Authority implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 接口地址
     */
    private String url;

    /**
     * HTTP请求方法,GET,POST
     */
    private String method;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}