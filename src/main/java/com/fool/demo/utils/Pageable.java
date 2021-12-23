package com.fool.demo.utils;

/**
 * @author fool
 * @date 2021/12/23 9:45
 */
public interface Pageable {

    int getPageNum();

    int getPageSize();

    String getOrderBy();

}
