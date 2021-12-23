package com.fool.demo.entity;

import com.fool.demo.utils.Pageable;
import lombok.Data;

/**
 * @author fool
 * @date 2021/12/23 16:59
 */
@Data
public class CommonQUERY implements Pageable {
    private int pageNum;

    private int pageSize;

    private String orderBy;


}
