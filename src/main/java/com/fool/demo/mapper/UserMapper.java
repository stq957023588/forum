package com.fool.demo.mapper;

import com.fool.demo.domain.User;

/**
 * @Entity com.fool.demo.domain.User
 */
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByEmail(String email);
}




