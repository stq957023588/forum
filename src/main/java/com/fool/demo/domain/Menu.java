package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName menu
 */
@Data
public class Menu implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 菜单对应地址
     */
    private String url;

    /**
     * 菜单对应组件
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 上级菜单ID
     */
    private Integer parentMenuId;

    /**
     * 是否启用
     */
    private Integer enable;

    /**
     * 创建人ID
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    /**
     * 删除人
     */
    private Integer deleter;

    /**
     * 删除时间
     */
    private Date deleteTime;

    private static final long serialVersionUID = 1L;
}