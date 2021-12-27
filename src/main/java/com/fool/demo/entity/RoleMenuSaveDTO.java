package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/27 15:39
 */
@Data
public class RoleMenuSaveDTO {
    private Integer roleId;

    private List<MenuDTO> menus;
}
