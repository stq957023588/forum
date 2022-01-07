package com.fool.demo.service;

import com.fool.demo.domain.DataRule;
import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.DataRuleDTO;
import com.fool.demo.entity.DataRuleGroupMemberQUERY;
import com.fool.demo.mapper.DataRuleMapper;
import com.fool.demo.mapstruct.DataRuleConvertor;
import com.fool.demo.utils.PageUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fool
 * @date 2022/1/4 10:22
 */
@Service
public class DataRuleService {


    private final DataRuleMapper dataRuleMapper;

    public DataRuleService(DataRuleMapper dataRuleMapper) {
        this.dataRuleMapper = dataRuleMapper;
    }


    public void addDataRule(DataRuleDTO dto) {
        DataRule dataRule = DataRuleConvertor.INSTANCE.toDomain(dto);
        dataRuleMapper.insertSelective(dataRule);
    }

    public void updateDataRule(DataRuleDTO dto) {
        DataRule origin = dataRuleMapper.selectByPrimaryKey(dto.getId().longValue());
        DataRule dataRule = DataRuleConvertor.INSTANCE.toDomain(dto);
        dataRule.setCreateTime(origin.getCreateTime());
        dataRuleMapper.updateByPrimaryKey(dataRule);

    }

    public void deleteDataRule(List<Integer> idList) {
        dataRuleMapper.deleteByIdList(idList);
    }

    public PageInfo<DataRuleDTO> getDataRules(CommonQUERY query) {
        ISelect select = dataRuleMapper::selectAll;
        return PageUtils.doSelect(select, query, DataRuleConvertor.INSTANCE::toDataTransferObject);
    }

    public PageInfo<DataRuleDTO> getDataRuleSelection(DataRuleGroupMemberQUERY query) {
        ISelect select = () -> dataRuleMapper.selectDontHaveByGroup(query.getDataRuleGroupId());
        return PageUtils.doSelect(select, query, DataRuleConvertor.INSTANCE::toDataTransferObject);
    }

    public PageInfo<DataRuleDTO> getDataRuleByGroup(DataRuleGroupMemberQUERY query) {
        ISelect select = () -> dataRuleMapper.selectByGroup(query);
        return PageUtils.doSelect(select, query, DataRuleConvertor.INSTANCE::toDataTransferObject);
    }





}
