package com.fool.demo.controller;

import com.fool.demo.entity.MenuDTO;
import com.fool.demo.entity.MenuRoleDTO;
import com.fool.demo.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/21 11:01
 */
@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value = "role-menus", method = RequestMethod.GET)
    public List<MenuRoleDTO> getLimitedMenus() {
        return menuService.getRoleMenus();
    }

    @RequestMapping(value = "menus", method = RequestMethod.GET)
    public List<MenuDTO> getMenus() {
        return menuService.getMenus();
    }

    @RequestMapping(value = "menus", method = RequestMethod.POST)
    public void add(MenuDTO menu) {
        menuService.add(menu);
    }


}
