package com.fool.demo.service;

import com.fool.demo.domain.Menu;
import com.fool.demo.domain.MenuExtra;
import com.fool.demo.entity.MenuDTO;
import com.fool.demo.entity.MenuRoleDTO;
import com.fool.demo.entity.MenuMeta;
import com.fool.demo.mapper.MenuMapper;
import com.fool.demo.mapstruct.MenuConvertor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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
        menuMapper.insertSelective(m);
    }

    public List<MenuDTO> getMenus() {
        List<Menu> menus = menuMapper.selectAll();
        return menus.stream().map(MenuConvertor.INSTANCE::toDataTransferObject).collect(Collectors.toList());
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

                currentMenu = MenuConvertor.INSTANCE.toMenuRole(menuExtra);
                currentMenu.setMeta(menuMeta);

                menus.add(currentMenu);
                prev = menuExtra;
                continue;
            }

            currentMenu.getMeta().getRoles().add(menuExtra.getRole());
        }


        List<MenuRoleDTO> roots = menus.stream().filter(e -> e.getParentMenuId() == null).collect(Collectors.toList());

        List<MenuRoleDTO> children = menus.stream()
                .filter(e -> e.getParentMenuId() != null)
                .collect(Collectors.toList());

        List<MenuRoleDTO> currentFloor = roots;
        List<MenuRoleDTO> nextFloor = new ArrayList<>();

        while (!children.isEmpty()) {
            Iterator<MenuRoleDTO> iterator = children.iterator();
            while (iterator.hasNext()) {
                MenuRoleDTO next = iterator.next();
                if (mountIfContainsParent(currentFloor, next)) {
                    nextFloor.add(next);
                    iterator.remove();
                }
            }
            currentFloor = nextFloor;
            nextFloor = new ArrayList<>();
        }

        return roots;
    }

    private boolean mountIfContainsParent(List<MenuRoleDTO> list, MenuRoleDTO menu) {
        for (MenuRoleDTO parent : list) {
            if (parent.getId().equals(menu.getParentMenuId())) {
                List<MenuRoleDTO> children = Optional.of(parent).map(MenuRoleDTO::getChildren).orElse(new ArrayList<>());
                children.add(menu);
                parent.setChildren(children);
                return true;
            }
        }
        return false;
    }

}
