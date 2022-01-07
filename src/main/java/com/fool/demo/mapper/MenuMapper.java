package com.fool.demo.mapper;

import com.fool.demo.domain.Menu;
import com.fool.demo.domain.MenuExtra;
import com.fool.demo.entity.MenuDTO;
import com.fool.demo.utils.PrimarySelector;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.Menu
 */
@Mapper
public interface MenuMapper extends PrimarySelector<Menu> {

    int deleteByPrimaryKey(Long id);

    int deleteRoleMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    int deleteLogicByIdList(@Param("idList") List<Integer> idList);

    int insert(Menu record);

    int insertSelective(Menu record);

    int insertRoleMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    @Override
    Menu selectByPrimaryKey(Long id);

    Menu selectByUrl(@Param("url") String url);

    Menu selectByUrlAndNotEqualsId(@Param("url") String url, @Param("id") Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<MenuExtra> selectAllWithRole();

    List<Menu> selectWhiteList();

    List<Menu> selectLimitedMenus();

    List<Menu> selectAll();

    List<MenuDTO> selectWithParentMenuName();

    List<Menu> selectRoleMenu(Integer roleId);

    List<Menu> selectRoleMenuTreeLeaf(Integer roleId);

    List<Integer> selectMenuIdByRoleId(Integer roleId);
}




