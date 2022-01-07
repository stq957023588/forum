package com.fool.demo.mapper;

import com.fool.demo.domain.DataRule;
import com.fool.demo.entity.DataRuleGroupMemberQUERY;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.DataRule
 */
@Mapper
public interface DataRuleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(DataRule record);

    int insertSelective(DataRule record);

    DataRule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DataRule record);

    int updateByPrimaryKey(DataRule record);

    int deleteByIdList(@Param("idList") List<Integer> idList);


    List<DataRule> selectByGroup(DataRuleGroupMemberQUERY query);

    List<DataRule> selectDontHaveByGroup(@Param("dataRuleGroupId")Integer dataRuleGroupId);

    List<DataRule> selectAll();




}




