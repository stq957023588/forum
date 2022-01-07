package com.fool.demo.utils;

/**
 * @author fool
 * @date 2021/12/30 14:25
 */
public  interface PrimarySelector<T>{

    T selectByPrimaryKey(Long id);

}
