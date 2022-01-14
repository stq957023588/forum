package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName post
 */
@Data
public class Post implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容,图片采用URL存储
     */
    private String content;

    /**
     * 创建人,USER的ID字段
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 删除时间
     */
    private Date deleteTime;

    private static final long serialVersionUID = 1L;
}