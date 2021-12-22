package com.fool.demo.domain;

import lombok.Data;

import java.util.Date;

/**
 * @TableName orders_0
 */
@Data
public class Order {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Integer value;

    /**
     *
     */
    private Date createTime;

}