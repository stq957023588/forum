package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * springboot整合flyway测试表
 * @TableName student
 */
@Data
public class Student implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer sex;

    /**
     * 
     */
    private Integer grade;

    /**
     * 
     */
    private Integer classNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}