package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName dictionary_type
 */
@Data
public class DictionaryType implements Serializable {
    /**
     * 
     */
    private Integer code;

    /**
     * 
     */
    private String label;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}