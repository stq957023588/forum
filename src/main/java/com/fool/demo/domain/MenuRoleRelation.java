package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName menu_role_relation
 */
@Data
public class MenuRoleRelation implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 创建人,USER的ID字段
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}