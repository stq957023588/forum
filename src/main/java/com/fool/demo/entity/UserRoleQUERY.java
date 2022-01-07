package com.fool.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fool
 * @date 2021/12/29 17:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleQUERY extends CommonQUERY{
    private Integer userId;
}
