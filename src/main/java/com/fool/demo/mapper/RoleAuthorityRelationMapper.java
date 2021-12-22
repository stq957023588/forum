package com.fool.demo.mapper;

import com.fool.demo.domain.RoleAuthorityRelation;

/**
 * @Entity com.fool.demo.domain.RoleAuthorityRelation
 */
public interface RoleAuthorityRelationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RoleAuthorityRelation record);

    int insertSelective(RoleAuthorityRelation record);

    RoleAuthorityRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleAuthorityRelation record);

    int updateByPrimaryKey(RoleAuthorityRelation record);

}




