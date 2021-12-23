package com.fool.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fool
 * @date 2021/12/21 11:03
 */
@Data
public class MenuDTO {

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

    private String parentMenuName;
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


}
