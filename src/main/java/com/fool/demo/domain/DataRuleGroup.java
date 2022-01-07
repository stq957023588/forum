package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName data_rule_group
 */
@Data
public class DataRuleGroup implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 规则组组名
     */
    private String name;

    /**
     * 是否启用
     */
    private Integer enable;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}