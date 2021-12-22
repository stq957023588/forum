package com.fool.demo.mapper;

import com.fool.demo.domain.MenuRoleRelation;

/**
 * @Entity com.fool.demo.domain.MenuRoleRelation
 */
public interface MenuRoleRelationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(MenuRoleRelation record);

    int insertSelective(MenuRoleRelation record);

    MenuRoleRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuRoleRelation record);

    int updateByPrimaryKey(MenuRoleRelation record);

}




