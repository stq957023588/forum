package com.fool.demo.mapper;

import com.fool.demo.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.Role
 */
@Mapper
public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    int insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    Role selectByPrimaryKey(Long id);

    List<Role> selectByIdList(@Param("idList") List<Integer> idList);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectByUserId(Integer userId);

    List<Role> selectRolesByUrl(String url);

    List<Role> selectRolesByUrlAndMethod(@Param("url") String url, @Param("method") String method);

    List<Role> selectUserDontHaveRoles(@Param("userId") Integer userId);

    Role selectByName(String name);

    Role selectByNameAndNotEqualsId(@Param("name") String name, @Param("id") Integer id);

    int updateNameById(@Param("name") String name, @Param("id") Integer id);

    int updateEnableById(@Param("enable") Integer enable, @Param("id") Integer id);

    List<Role> selectAll();

    int deleteLogicByIdList(@Param("idList") List<Integer> idList);

    int deleteUserRole(@Param("userRoleIdList")List<Integer> userRoleIdList);
}




