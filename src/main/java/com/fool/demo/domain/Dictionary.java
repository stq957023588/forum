package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName dictionary
 */
@Data
public class Dictionary implements Serializable {
    /**
     * 
     */
    private Integer type;

    /**
     * 
     */
    private Integer code;

    /**
     * 
     */
    private String label;

    /**
     * 
     */
    private String value;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}