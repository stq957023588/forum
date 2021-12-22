package com.fool.demo.mapper;

import com.fool.demo.domain.Authority;
import com.fool.demo.domain.Role;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.Authority
 */
public interface AuthorityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);

    List<Role> selectRolesByUrl(String url);
}




