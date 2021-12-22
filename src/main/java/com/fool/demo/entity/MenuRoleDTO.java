package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/20 15:41
 */
@Data
public class MenuRoleDTO {

    private Integer id;

    private String path;

    private String component;

    private Integer parentMenuId;

    private MenuMeta meta;

    private List<MenuRoleDTO> children;

}
