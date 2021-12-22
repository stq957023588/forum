package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName User_Role_Relation
 */
@Data
public class UserRoleRelation implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 创建时间 
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}