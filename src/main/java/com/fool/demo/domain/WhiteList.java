package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName white_list
 */
@Data
public class WhiteList implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 菜单或者权限ID
     */
    private Integer whiteId;

    /**
     * 类型
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}