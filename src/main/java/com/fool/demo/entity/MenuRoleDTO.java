package com.fool.demo.entity;

import com.fool.demo.utils.TreeNode;
import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/20 15:41
 */
@Data
public class MenuRoleDTO implements TreeNode<MenuRoleDTO,Integer> {

    private Integer id;

    private String path;

    private String component;

    private Integer parentMenuId;

    private MenuMeta meta;

    private List<MenuRoleDTO> children;

    @Override
    public Integer getParentId() {
        return this.parentMenuId;
    }
}
