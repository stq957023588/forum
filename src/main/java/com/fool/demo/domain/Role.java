package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName role
 */
@Data
public class Role implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 是否启用,0否,1是
     */
    private Integer enable;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 逻辑删除时间
     */
    private Date deleteTime;

    private static final long serialVersionUID = 1L;
}