package com.fool.demo.entity;

import com.fool.demo.utils.Pageable;
import lombok.Data;

/**
 * @author fool
 * @date 2021/12/23 10:52
 */
@Data
public class MenuQUERY implements Pageable {

    private int pageNum;

    private int pageSize;

    private String orderBy;



}
