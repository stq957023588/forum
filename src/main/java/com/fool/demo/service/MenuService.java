package com.fool.demo.service;

import com.fool.demo.domain.Menu;
import com.fool.demo.domain.MenuExtra;
import com.fool.demo.entity.*;
import com.fool.demo.mapper.MenuMapper;
import com.fool.demo.mapstruct.MenuConvertor;
import com.fool.demo.utils.PageUtils;
import com.fool.demo.utils.TreeUtils;
import com.fool.demo.utils.UserUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fool
 * @date 2021/12/20 15:40
 */
@Service
public class MenuService {

    private final MenuMapper menuMapper;

    public MenuService(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }


    public void add(MenuDTO menu) {
        Menu m = MenuConvertor.INSTANCE.toDomain(menu);

        CustomizeUser currentUser = UserUtils.getCurrentUserDetails();
        m.setCreator(currentUser.getId());
        menuMapper.insertSelective(m);
    }

    public void update(MenuDTO menu) {
        Menu exist = menuMapper.selectByUrlAndNotEqualsId(menu.getUrl(), menu.getId());
        if (exist != null){
            throw new RuntimeException("已存在相同路径");
        }
        Menu origin = menuMapper.selectByPrimaryKey(menu.getId().longValue());

        Menu m = MenuConvertor.INSTANCE.toDomain(menu, origin);
        menuMapper.updateByPrimaryKey(m);
    }

    public PageInfo<MenuDTO> getMenus(MenuQUERY query) {
        ISelect select = menuMapper::selectWithParentMenuName;
        return PageUtils.doSelect(select, query);
    }

    public List<MenuTreeNode> getMenuTree() {
        List<Menu> menus = menuMapper.selectAll();
        List<MenuTreeNode> treeNodeList = menus.stream().map(MenuConvertor.INSTANCE::toTreeNode).collect(Collectors.toList());

        return TreeUtils.listToTree(treeNodeList);
    }


    public List<MenuRoleDTO> getRoleMenus() {
        List<MenuExtra> menuExtras = menuMapper.selectAllWithRole();

        ArrayList<MenuRoleDTO> menus = new ArrayList<>();

        MenuRoleDTO currentMenu = null;

        MenuExtra prev = null;

        for (MenuExtra menuExtra : menuExtras) {
            if (currentMenu == null || menuExtra.getRole() == null || !menuExtra.getName().equals(prev.getName())) {
                MenuMeta menuMeta = new MenuMeta();
                Optional.ofNullable(menuExtra.getRole()).ifPresent(role -> {
                    ArrayList<String> roles = new ArrayList<>();
                    roles.add(role);
                    menuMeta.setRoles(roles);
                });
                menuMeta.setTitle(menuExtra.getName());
                menuMeta.setIcon(menuExtra.getIcon());

                currentMenu = MenuConvertor.INSTANCE.toMenuRole(menuExtra);
                currentMenu.setMeta(menuMeta);

                menus.add(currentMenu);
                prev = menuExtra;
                continue;
            }

            currentMenu.getMeta().getRoles().add(menuExtra.getRole());
        }

        return TreeUtils.listToTree(menus);
    }

}
