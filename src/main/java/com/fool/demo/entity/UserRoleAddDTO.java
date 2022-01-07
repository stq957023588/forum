package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/30 10:32
 */
@Data
public class UserRoleAddDTO {

    private Integer userId;

    private List<RoleDTO> roles;
}
