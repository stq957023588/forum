package com.fool.demo.mapper;

import com.fool.demo.domain.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.Role
 */
@Repository
public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectByUserId(Integer userId);

    Role selectByName(String name);

    int updateNameById(@Param("name") String name, @Param("id") Integer id);

    int updateEnableById(@Param("enable")Integer enable,@Param("id")Integer id);


    List<Role> selectAll();
}




