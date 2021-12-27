package com.fool.demo.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fool
 * @date 2021/12/22 14:11
 */
public class TreeUtils {

    public static <T extends TreeNode<?, ?>> List<T> listToTree(List<T> list) {
        List<T> roots = list.stream().filter(e -> e.getParentId() == null).collect(Collectors.toList());

        List<T> children = list.stream()
                .filter(e -> e.getParentId() != null)
                .collect(Collectors.toList());

        List<T> currentFloor = roots;
        List<T> nextFloor = new ArrayList<>();

        boolean flag = false;
        while (!children.isEmpty()) {
            Iterator<T> iterator = children.iterator();
            if (flag) {
                break;
            }
            flag = true;
            while (iterator.hasNext()) {
                T next = iterator.next();
                if (mountIfContainsParent(currentFloor, next)) {
                    flag = false;
                    nextFloor.add(next);
                    iterator.remove();
                }
            }
            currentFloor = nextFloor;
            nextFloor = new ArrayList<>();
        }

        return roots;
    }

    private static <T extends TreeNode<?, ?>> boolean mountIfContainsParent(List<T> list, T item) {
        for (T parent : list) {
            if (parent.getId().equals(item.getParentId())) {
                List children = Optional.of(parent).map(e -> e.getChildren()).orElse(new ArrayList<>());
                children.add(item);
                parent.setChildren(children);
                return true;
            }
        }
        return false;
    }


}
