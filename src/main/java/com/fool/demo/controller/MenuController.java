package com.fool.demo.controller;

import com.fool.demo.entity.MenuDTO;
import com.fool.demo.entity.MenuQUERY;
import com.fool.demo.entity.MenuRoleDTO;
import com.fool.demo.entity.MenuTreeNode;
import com.fool.demo.service.MenuService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/21 11:01
 */
@Slf4j
@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value = "role-menu-tree", method = RequestMethod.GET)
    public List<MenuRoleDTO> getRoleMenuTree() {
        return menuService.getRoleMenus();
    }

    @RequestMapping(value = "menu-tree", method = RequestMethod.GET)
    public List<MenuTreeNode> getMenuTree() {
        return menuService.getMenuTree();
    }


    @RequestMapping(value = "menu", method = RequestMethod.GET)
    public PageInfo<MenuDTO> getMenus(MenuQUERY query) {
        return menuService.getMenus(query);
    }

    @RequestMapping(value = "menu", method = RequestMethod.POST)
    public void add(@RequestBody MenuDTO menu) {
        menuService.add(menu);
        // log.info("MENU:" + menu);
    }

    @RequestMapping(value = "menu", method = RequestMethod.PUT)
    public void update(@RequestBody MenuDTO menu) {
        menuService.update(menu);
    }


}
