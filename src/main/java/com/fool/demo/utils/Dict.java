package com.fool.demo.utils;

/**
 * @author fool
 * @date 2022/1/7 11:08
 */
public interface Dict<Type,Code> {

    Type getType();

    Code getCode();

    String getLabel();

}
