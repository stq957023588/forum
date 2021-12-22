package com.fool.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName role_authority_relation
 */
@Data
public class RoleAuthorityRelation implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 角色表ID
     */
    private Integer roleId;

    /**
     * 权限表ID
     */
    private Integer authorityId;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}