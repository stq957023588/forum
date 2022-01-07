package com.fool.demo.controller;

import com.fool.demo.entity.*;
import com.fool.demo.service.MenuService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "角色菜单树", notes = "包含当前菜单所需要的权限的菜单树")
    @RequestMapping(value = "role-menu-tree", method = RequestMethod.GET)
    public List<MenuRoleDTO> getRoleMenuTree() {
        return menuService.getRoleMenuTree();
    }

    @ApiOperation(value = "角色菜单列表", notes = "查询角色列表")
    @RequestMapping(value = "role-menu", method = RequestMethod.GET)
    public List<MenuDTO> getRoleMenu(RoleMenuQUERY query) {
        return menuService.getRoleMenus(query);
    }

    @ApiOperation(value = "新增角色菜单", notes = "新增角色菜单")
    @RequestMapping(value = "role-menu", method = RequestMethod.POST)
    public void addRoleMenu(@RequestBody RoleMenuSaveDTO dto) {
        menuService.saveRoleMenus(dto);
    }

    @ApiOperation(value = "菜单树", notes = "包含系统所有菜单的菜单树")
    @RequestMapping(value = "menu-tree", method = RequestMethod.GET)
    public List<MenuTreeNode> getMenuTree() {
        return menuService.getMenuTree();
    }

    @ApiOperation(value = "菜单列表", notes = "查询分页菜单列表")
    @RequestMapping(value = "menu", method = RequestMethod.GET)
    public PageInfo<MenuDTO> getMenus(MenuQUERY query) {
        return menuService.getMenus(query);
    }

    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    @RequestMapping(value = "menu", method = RequestMethod.POST)
    public void add(@RequestBody MenuDTO menu) {
        menuService.add(menu);
        // log.info("MENU:" + menu);
    }

    @ApiOperation(value = "更新菜单", notes = "更新菜单信息")
    @RequestMapping(value = "menu", method = RequestMethod.PUT)
    public void update(@RequestBody MenuDTO menu) {
        menuService.update(menu);
    }

    @ApiOperation(value = "删除菜单", notes = "逻辑删除菜单")
    @RequestMapping(value = "menu", method = RequestMethod.DELETE)
    public void delete(@RequestBody List<Integer> idList) {
        menuService.delete(idList);
    }

    @ApiOperation(value = "需要权限的菜单", notes = "不是白名单中的菜单")
    @RequestMapping(value = "limited-menu", method = RequestMethod.GET)
    public PageInfo<MenuDTO> getLimitedMenus(CommonQUERY query) {
        return menuService.getLimitedMenus(query);
    }

}
