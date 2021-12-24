package com.fool.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fool
 * @date 2021/12/24 16:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleAuthorityQUERY extends CommonQUERY{
    private Integer roleId;


}
