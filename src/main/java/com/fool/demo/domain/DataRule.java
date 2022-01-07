package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName data_rule
 */
@Data
public class DataRule implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 规则名
     */
    private String name;

    /**
     * 规则字段
     */
    private String field;

    /**
     * 规则条件.大于,等于,小于
     */
    private String condition;

    /**
     * 规则值
     */
    private String value;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}