package com.fool.demo.entity;

import com.fool.demo.utils.TreeNode;
import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/22 14:21
 */
@Data
public class MenuTreeNode implements TreeNode<MenuTreeNode,Integer> {

    private Integer id;

    private String name;

    private String url;

    private Integer parentMenuId;

    private List<MenuTreeNode> children;


    @Override
    public Integer getParentId() {
        return this.parentMenuId;
    }
}
