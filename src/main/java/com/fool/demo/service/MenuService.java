package com.fool.demo.service;

import com.fool.demo.domain.Menu;
import com.fool.demo.domain.MenuExtra;
import com.fool.demo.domain.Role;
import com.fool.demo.entity.*;
import com.fool.demo.mapper.MenuMapper;
import com.fool.demo.mapper.RoleMapper;
import com.fool.demo.mapstruct.MenuConvertor;
import com.fool.demo.utils.PageUtils;
import com.fool.demo.utils.TreeUtils;
import com.fool.demo.utils.UserUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Comparator;
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

    private final RoleMapper roleMapper;

    private final SqlSessionFactory sqlSessionFactory;

    private Role superRole;

    public MenuService(MenuMapper menuMapper, RoleMapper roleMapper, SqlSessionFactory sqlSessionFactory) {
        this.menuMapper = menuMapper;
        this.roleMapper = roleMapper;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Value("${security.super-role}")
    public void setSuperRole(String superRole) {
        this.superRole = new Role(superRole);
    }

    public void delete(List<Integer> idList) {
        menuMapper.deleteLogicByIdList(idList);
    }

    public void add(MenuDTO menu) {
        Menu exist = menuMapper.selectByUrl(menu.getUrl());
        if (exist != null) {
            throw new RuntimeException("已存在相同路径");
        }

        Menu m = MenuConvertor.INSTANCE.toDomain(menu);

        CustomizeUser currentUser = UserUtils.getCurrentUserDetails();
        m.setCreator(currentUser.getId());
        menuMapper.insertSelective(m);
    }

    public void update(MenuDTO menu) {
        Menu exist = menuMapper.selectByUrlAndNotEqualsId(menu.getUrl(), menu.getId());
        if (exist != null) {
            throw new RuntimeException("已存在相同路径");
        }
        Menu origin = menuMapper.selectByPrimaryKey(menu.getId().longValue());

        Menu m = MenuConvertor.INSTANCE.toDomain(menu, origin);
        menuMapper.updateByPrimaryKey(m);
    }

    public PageInfo<MenuDTO> getLimitedMenus(CommonQUERY query) {
        ISelect select = menuMapper::selectLimitedMenus;
        return PageUtils.doSelect(select, query, MenuConvertor.INSTANCE::toDataTransferObject);
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

    public List<MenuDTO> getRoleMenus(RoleMenuQUERY query) {
        Role role = roleMapper.selectByPrimaryKey(query.getRoleId().longValue());
        Assert.notNull(role, "角色不存在");
        List<Menu> menus;
        if (role.getName().equals(superRole.getName())) {
            menus = menuMapper.selectAll();
        } else if (query.isLeafOnly()) {
            menus = menuMapper.selectRoleMenuTreeLeaf(query.getRoleId());
        } else {
            menus = menuMapper.selectRoleMenu(query.getRoleId());
        }
        return menus.stream().map(MenuConvertor.INSTANCE::toDataTransferObject).collect(Collectors.toList());
    }

    public void saveRoleMenus(RoleMenuSaveDTO dto) {
        Role role = roleMapper.selectByPrimaryKey(dto.getRoleId().longValue());

        if (role == null) {
            throw new RuntimeException("角色不存在");
        }
        if (role.getName().equals(superRole.getName())) {
            throw new RuntimeException("无法修改超级管理员菜单");
        }

        List<Integer> existMenuIdList = menuMapper.selectMenuIdByRoleId(dto.getRoleId());

        List<Integer> newMenuIdList = dto.getMenus().stream().map(MenuDTO::getId).sorted().collect(Collectors.toList());

        int existIndex = 0;
        int newIndex = 0;

        List<Integer> deleteIdList = new ArrayList<>();
        List<Integer> insertIdList = new ArrayList<>();

        while (existIndex < existMenuIdList.size() && newIndex < newMenuIdList.size()) {
            int existMenuId = existMenuIdList.get(existIndex);
            int newMenuId = newMenuIdList.get(newIndex);
            if (newMenuId > existMenuId) {
                deleteIdList.add(existMenuId);
                existIndex++;
                continue;
            }
            if (newMenuId < existMenuId) {
                insertIdList.add(newMenuId);
                newIndex++;
                continue;
            }
            existIndex++;
            newIndex++;
        }

        for (int i = existIndex; i < existMenuIdList.size(); i++) {
            deleteIdList.add(existMenuIdList.get(i));
        }
        for (int i = newIndex; i < newMenuIdList.size(); i++) {
            insertIdList.add(newMenuIdList.get(i));
        }

        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            MenuMapper mapper = sqlSession.getMapper(MenuMapper.class);
            for (Integer menuId : deleteIdList) {
                mapper.deleteRoleMenu(dto.getRoleId(), menuId);
            }
            for (Integer menuId : insertIdList) {
                mapper.insertRoleMenu(dto.getRoleId(), menuId);
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<MenuRoleDTO> getRoleMenuTree() {
        List<MenuExtra> menuExtras = menuMapper.selectAllWithRole();
        List<Menu> whiteLists = menuMapper.selectWhiteList();
        List<String> whiteListPaths = whiteLists.stream().map(Menu::getUrl).sorted().collect(Collectors.toList());

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
        menus.sort(Comparator.comparing(MenuRoleDTO::getPath));

        int whiteListIndex = 0;
        int menuIndex = 0;

        while (whiteListIndex < whiteListPaths.size() && menuIndex < menus.size()) {
            String whiteListPath = whiteListPaths.get(whiteListIndex);
            MenuRoleDTO menu = menus.get(menuIndex);
            String menuPath = menu.getPath();

            int compare = menuPath.compareTo(whiteListPath);

            if (compare < 0) {
                List<String> roles = Optional.ofNullable(menu.getMeta().getRoles()).orElse(new ArrayList<>(1));
                roles.add(superRole.getName());
                menu.getMeta().setRoles(roles);
                menuIndex++;
                continue;
            }

            if (compare > 0) {
                whiteListIndex++;
                continue;
            }
            menu.getMeta().setRoles(null);
            whiteListIndex++;
            menuIndex++;
        }

        for (int i = menuIndex; i < menus.size(); i++) {
            MenuRoleDTO menu = menus.get(i);
            List<String> roles = Optional.ofNullable(menu.getMeta().getRoles()).orElse(new ArrayList<>(1));
            roles.add(superRole.getName());
            menu.getMeta().setRoles(roles);
        }


        return TreeUtils.listToTree(menus);
    }

}
