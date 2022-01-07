package com.fool.demo.mapper;

import com.fool.demo.domain.AuthorityAndDataRuleGroup;
import com.fool.demo.domain.DataRuleGroup;
import com.fool.demo.entity.AuthorityDataRuleGroupQUERY;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.DataRuleGroup
 */
public interface DataRuleGroupMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DataRuleGroup record);

    int insertSelective(DataRuleGroup record);

    int insertDataRuleGroupMember(@Param("dataRuleGroupId") Integer dataGroupId, @Param("dataRuleId") Integer dataRuleId);

    int insertAuthorityDataRuleGroup(@Param("authorityId") Integer authorityId, @Param("dataRuleGroupId") Integer dataRuleGroupId);

    int insertRoleAuthorityDataRuleGroup(@Param("roleAuthorityRelationId") Integer roleAuthorityRelationId, @Param("dataRuleGroupId") Integer dataRuleGroupId);

    DataRuleGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DataRuleGroup record);

    int updateByPrimaryKey(DataRuleGroup record);

    int deleteByIdList(@Param("idList") List<Integer> idList);

    int deleteRoleAuthorityDataRuleGroup(@Param("roleId") Integer roleId);

    Integer selectRoleAuthorityRelationId(@Param("roleId") Integer roleId, @Param("authorityId") Integer authorityId);

    int deleteDataRuleGroupMembers(@Param("dataRuleGroupId") Integer dataRuleGroupId, @Param("dataRuleIdList") List<Integer> dataRuleIdList);

    int deleteAuthorityDataRuleGroups(@Param("authorityId") Integer authorityId, @Param("dataRuleGroupIdList") List<Integer> dataRuleGroupIdList);

    List<DataRuleGroup> selectByAuthority(AuthorityDataRuleGroupQUERY query);

    List<DataRuleGroup> selectDontHaveByAuthority(@Param("authorityId") Integer authorityId);

    List<DataRuleGroup> selectAll();

    List<AuthorityAndDataRuleGroup> selectAuthorityAndDataRuleGroup();

    List<AuthorityAndDataRuleGroup> selectCheckedAuthorityAndDataRuleGroup(@Param("roleId") Integer roleId);
}




