package com.fool.demo.mapper;

import com.fool.demo.domain.UserRoleRelation;

/**
 * @Entity com.fool.demo.domain.UserRoleRelation
 */
public interface UserRoleRelationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRelation record);

    int insertSelective(UserRoleRelation record);

    UserRoleRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleRelation record);

    int updateByPrimaryKey(UserRoleRelation record);

}




