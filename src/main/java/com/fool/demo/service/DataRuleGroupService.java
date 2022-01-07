package com.fool.demo.service;

import com.fool.demo.domain.AuthorityAndDataRuleGroup;
import com.fool.demo.domain.DataRuleGroup;
import com.fool.demo.entity.*;
import com.fool.demo.mapper.DataRuleGroupMapper;
import com.fool.demo.mapstruct.DataRuleGroupConvertor;
import com.fool.demo.utils.PageUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fool
 * @date 2022/1/4 11:14
 */
@Service
public class DataRuleGroupService {

    private final DataRuleGroupMapper dataRuleGroupMapper;

    private final RoleService roleService;

    public DataRuleGroupService(DataRuleGroupMapper dataRuleGroupMapper, RoleService roleService) {
        this.dataRuleGroupMapper = dataRuleGroupMapper;
        this.roleService = roleService;
    }

    public void addDataRuleGroup(DataRuleGroupDTO dto) {
        DataRuleGroup dataRuleGroup = DataRuleGroupConvertor.INSTANCE.toDomain(dto);
        dataRuleGroupMapper.insertSelective(dataRuleGroup);
    }

    public void updateDataRuleGroup(DataRuleGroupDTO dto) {
        DataRuleGroup origin = dataRuleGroupMapper.selectByPrimaryKey(dto.getId().longValue());
        DataRuleGroup dataRuleGroup = DataRuleGroupConvertor.INSTANCE.toDomain(dto);
        dataRuleGroup.setCreateTime(origin.getCreateTime());
        dataRuleGroupMapper.updateByPrimaryKey(dataRuleGroup);

    }

    public void deleteDataRuleGroup(List<Integer> idList) {
        dataRuleGroupMapper.deleteByIdList(idList);
    }

    public PageInfo<DataRuleGroupDTO> getDataRuleGroup(CommonQUERY query) {
        ISelect select = dataRuleGroupMapper::selectAll;
        return PageUtils.doSelect(select, query, DataRuleGroupConvertor.INSTANCE::toDataTransferObject);
    }

    public PageInfo<DataRuleGroupDTO> getAuthorityDataRuleGroups(AuthorityDataRuleGroupQUERY query) {
        ISelect select = () -> dataRuleGroupMapper.selectByAuthority(query);
        return PageUtils.doSelect(select, query, DataRuleGroupConvertor.INSTANCE::toDataTransferObject);
    }

    public PageInfo<DataRuleGroupDTO> getDataRuleGroupSelection(AuthorityDataRuleGroupQUERY query) {
        ISelect select = () -> dataRuleGroupMapper.selectDontHaveByAuthority(query.getAuthorityId());
        return PageUtils.doSelect(select, query, DataRuleGroupConvertor.INSTANCE::toDataTransferObject);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDataRuleGroupMembers(DataRuleGroupDTO dto) {
        for (DataRuleDTO dataRule : dto.getDataRules()) {
            dataRuleGroupMapper.insertDataRuleGroupMember(dto.getId(), dataRule.getId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addAuthorityDataRuleGroup(AuthorityDataRuleGroupDTO dto) {
        for (DataRuleGroupDTO dataRuleGroup : dto.getDataRuleGroups()) {
            dataRuleGroupMapper.insertAuthorityDataRuleGroup(dto.getAuthorityId(), dataRuleGroup.getId());
        }
    }

    public void deleteAuthorityDataRuleGroup(AuthorityDataRuleGroupDTO dto) {
        List<Integer> dataRuleGroupIdList = dto.getDataRuleGroups().stream().map(DataRuleGroupDTO::getId).collect(Collectors.toList());
        dataRuleGroupMapper.deleteAuthorityDataRuleGroups(dto.getAuthorityId(), dataRuleGroupIdList);
    }

    public void deleteDataRuleGroupMembers(DataRuleGroupDTO dto) {
        List<Integer> dataRuleIdList = dto.getDataRules().stream().map(DataRuleDTO::getId).collect(Collectors.toList());
        dataRuleGroupMapper.deleteDataRuleGroupMembers(dto.getId(), dataRuleIdList);
    }

    public List<AuthorityDataRuleGroupTreeNode> getCheckedAuthorityDataRuleGroupTreeNodes(Integer roleId) {
        boolean superRole = roleService.isSuperRole(roleId);
        List<AuthorityAndDataRuleGroup> authorityAndDataRuleGroups;
        if (superRole){
            authorityAndDataRuleGroups = dataRuleGroupMapper.selectAuthorityAndDataRuleGroup();
        }else{
            authorityAndDataRuleGroups = dataRuleGroupMapper.selectCheckedAuthorityAndDataRuleGroup(roleId);
        }

        return authorityAndDataRuleGroups.stream().map(DataRuleGroupConvertor.INSTANCE::toTreeNode).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveRoleAuthorityDataRuleGroup(RoleAuthorityDataRuleGroupSaveDTO dto) {
        boolean superRole = roleService.isSuperRole(dto.getRoleId() );
        Assert.state(!superRole,"不可以修改超级管理员的数据规则");

        dataRuleGroupMapper.deleteRoleAuthorityDataRuleGroup(dto.getRoleId());

        for (AuthorityDataRuleGroupTreeNode treeNode : dto.getAuthorityDataRuleGroups()) {
            Integer roleAuthorityRelationId = dataRuleGroupMapper.selectRoleAuthorityRelationId(dto.getRoleId(), treeNode.getAuthorityId());
            dataRuleGroupMapper.insertRoleAuthorityDataRuleGroup(roleAuthorityRelationId, treeNode.getDataRuleGroupId());
        }
    }

    public List<AuthorityDataRuleGroupTreeNode> getAuthorityDataRuleGroupTree() {
        List<AuthorityAndDataRuleGroup> authorityAndDataRuleGroups = dataRuleGroupMapper.selectAuthorityAndDataRuleGroup();

        List<AuthorityDataRuleGroupTreeNode> tree = new ArrayList<>(authorityAndDataRuleGroups.size());

        Integer currentAuthorityId = null;
        AuthorityDataRuleGroupTreeNode treeNode = null;
        for (AuthorityAndDataRuleGroup authorityAndDataRuleGroup : authorityAndDataRuleGroups) {
            if (!authorityAndDataRuleGroup.getAuthorityId().equals(currentAuthorityId)) {
                Optional.ofNullable(treeNode).ifPresent(tree::add);
                treeNode = new AuthorityDataRuleGroupTreeNode();
                List<AuthorityDataRuleGroupTreeNode> children = new ArrayList<>();
                treeNode.setChildren(children);
                treeNode.setAuthorityId(authorityAndDataRuleGroup.getAuthorityId());
                treeNode.setName(authorityAndDataRuleGroup.getAuthorityName());
                currentAuthorityId = authorityAndDataRuleGroup.getAuthorityId();
            }

            AuthorityDataRuleGroupTreeNode leaf = new AuthorityDataRuleGroupTreeNode();
            leaf.setAuthorityId(authorityAndDataRuleGroup.getAuthorityId());
            leaf.setDataRuleGroupId(authorityAndDataRuleGroup.getDataRuleGroupId());
            leaf.setName(authorityAndDataRuleGroup.getDataRuleGroupName());
            treeNode.getChildren().add(leaf);
        }
        Optional.ofNullable(treeNode).ifPresent(tree::add);
        return tree;
    }
}
