package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@Data
public class User implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像,网络地址
     */
    private String headPic;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除;0为未删除,1为删除
     */
    private Integer deleted;

    /**
     * 逻辑删除时间
     */
    private Date deleteTime;

    private static final long serialVersionUID = 1L;
}