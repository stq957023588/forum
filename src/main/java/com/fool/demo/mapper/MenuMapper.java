package com.fool.demo.mapper;

import com.fool.demo.domain.Menu;
import com.fool.demo.domain.MenuExtra;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.Menu
 */
public interface MenuMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<MenuExtra> selectAllWithRole();

    List<Menu> selectAll();

}




