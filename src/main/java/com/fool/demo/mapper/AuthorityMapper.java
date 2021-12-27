package com.fool.demo.mapper;

import com.fool.demo.domain.Authority;
import com.fool.demo.entity.RoleAuthorityQUERY;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.Authority
 */
@Mapper
public interface AuthorityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);

    List<Authority> selectAll();

    List<Authority> selectByRoleAuthorityParams(RoleAuthorityQUERY query);

    List<Authority> selectRoleDontHaveAuthority(RoleAuthorityQUERY query);

    Authority selectByUrl(String url);

    Authority selectByUrlAndMethod(@Param("url") String url, @Param("method") String method);

    Authority selectByUrlAndMethodAndNotEqualsId(@Param("url") String url, @Param("method") String method, @Param("id") Integer id);

    int insertRoleAuthority(@Param("roleId")Integer roleId,@Param("authorityId")Integer authorityId);

    int deleteRoleAuthority(@Param("roleId")Integer role,@Param("authorityIds")List<Integer> authorityIds);

}




