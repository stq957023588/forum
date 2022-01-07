package com.fool.demo.entity;

import lombok.Data;

/**
 * @author fool
 * @date 2022/1/4 10:23
 */

@Data
public class DataRuleDTO {


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


}
