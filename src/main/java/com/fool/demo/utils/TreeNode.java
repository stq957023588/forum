package com.fool.demo.utils;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/22 14:05
 */
public interface TreeNode<T,P>{

    P getParentId();

    List<T> getChildren();

    void setChildren(List<T> list);

    P getId();

}
