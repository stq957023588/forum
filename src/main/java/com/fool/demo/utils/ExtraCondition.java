package com.fool.demo.utils;

import lombok.Data;

/**
 * @author fool
 * @date 2022/1/13 10:41
 */
@Data
public class ExtraCondition {

    private String key;

    private String operator;

    private Object value;

    private Class valueType;

}
