package com.fool.demo.controller;

import com.fool.demo.entity.*;
import com.fool.demo.service.DataRuleGroupService;
import com.fool.demo.service.DataRuleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fool
 * @date 2022/1/4 13:45
 */
@RestController
public class DataRuleController {

    private final DataRuleService dataRuleService;

    private final DataRuleGroupService dataRuleGroupService;

    public DataRuleController(DataRuleService dataRuleService, DataRuleGroupService dataRuleGroupService) {
        this.dataRuleService = dataRuleService;
        this.dataRuleGroupService = dataRuleGroupService;
    }

    @ApiOperation("新增数据规则")
    @RequestMapping(value = "data-rule", method = RequestMethod.POST)
    public void addDataRule(@RequestBody DataRuleDTO dto) {
        dataRuleService.addDataRule(dto);
    }

    @ApiOperation("修改数据规则")
    @RequestMapping(value = "data-rule", method = RequestMethod.PUT)
    public void updateDataRule(@RequestBody DataRuleDTO dto) {
        dataRuleService.updateDataRule(dto);
    }

    @ApiOperation("删除数据规则")
    @RequestMapping(value = "data-rule", method = RequestMethod.DELETE)
    public void deleteDataRule(@RequestBody List<Integer> idList) {
        dataRuleService.deleteDataRule(idList);
    }

    @ApiOperation("获取数据规则列表")
    @RequestMapping(value = "data-rule", method = RequestMethod.GET)
    public PageInfo<DataRuleDTO> getDataRules(CommonQUERY query) {
        return dataRuleService.getDataRules(query);
    }

    @ApiOperation("新增数据规则组")
    @RequestMapping(value = "data-rule-group", method = RequestMethod.POST)
    public void addDataRuleGroup(@RequestBody DataRuleGroupDTO dto) {
        dataRuleGroupService.addDataRuleGroup(dto);
    }

    @ApiOperation("修改数据规则组")
    @RequestMapping(value = "data-rule-group", method = RequestMethod.PUT)
    public void updateDataRuleGroup(@RequestBody DataRuleGroupDTO dto) {
        dataRuleGroupService.updateDataRuleGroup(dto);
    }

    @ApiOperation("删除数据规则组")
    @RequestMapping(value = "data-rule-group", method = RequestMethod.DELETE)
    public void deleteDataRuleGroup(@RequestBody List<Integer> dataRuleGroupIdList) {
        dataRuleGroupService.deleteDataRuleGroup(dataRuleGroupIdList);
    }

    @ApiOperation("数据规则组列表")
    @RequestMapping(value = "data-rule-group", method = RequestMethod.GET)
    public PageInfo<DataRuleGroupDTO> getDataRuleGroups(CommonQUERY query) {
        return dataRuleGroupService.getDataRuleGroup(query);
    }

    @ApiOperation("数据规则组规则")
    @RequestMapping(value = "data-rule-group-members", method = RequestMethod.GET)
    public PageInfo<DataRuleDTO> getDataRuleGroupMembers(DataRuleGroupMemberQUERY query) {
        return dataRuleService.getDataRuleByGroup(query);
    }

    @ApiOperation("删除数据规则组规则")
    @RequestMapping(value = "data-rule-group-members", method = RequestMethod.DELETE)
    public void deleteDataRuleGroupMembers(@RequestBody DataRuleGroupDTO dto) {
        dataRuleGroupService.deleteDataRuleGroupMembers(dto);
    }

    @ApiOperation("添加数据规则组规则")
    @RequestMapping(value = "data-rule-group-members", method = RequestMethod.POST)
    public void addDataRuleGroupMembers(@RequestBody DataRuleGroupDTO dto) {
        dataRuleGroupService.addDataRuleGroupMembers(dto);
    }

    @ApiOperation("添加权限的数据规则组")
    @RequestMapping(value = "authority-data-rule-groups", method = RequestMethod.POST)
    public void addAuthorityDataRuleGroups(@RequestBody AuthorityDataRuleGroupDTO dto) {
        dataRuleGroupService.addAuthorityDataRuleGroup(dto);
    }

    @ApiOperation("删除权限的数据规则组")
    @RequestMapping(value = "authority-data-rule-groups", method = RequestMethod.DELETE)
    public void deleteAuthorityDataRuleGroups(@RequestBody AuthorityDataRuleGroupDTO dto) {
        dataRuleGroupService.deleteAuthorityDataRuleGroup(dto);
    }


    @ApiOperation(value = "权限的数据规则组", notes = "根据权限查询数据规则组列表")
    @RequestMapping(value = "authority-data-rule-groups", method = RequestMethod.GET)
    public PageInfo<DataRuleGroupDTO> getAuthorityDataRuleGroups(AuthorityDataRuleGroupQUERY query) {
        return dataRuleGroupService.getAuthorityDataRuleGroups(query);
    }

    @ApiOperation(value = "数据规则选择列表", notes = "查询当前数据规则组中没有的数据规则")
    @RequestMapping(value = "data-rule-selection", method = RequestMethod.GET)
    public PageInfo<DataRuleDTO> getDataRuleSelections(DataRuleGroupMemberQUERY query) {
        return dataRuleService.getDataRuleSelection(query);

    }

    @ApiOperation(value = "数据规则选择列表", notes = "查询当前权限中没有的数据规则组")
    @RequestMapping(value = "data-rule-group-selection", method = RequestMethod.GET)
    public PageInfo<DataRuleGroupDTO> getDataRuleGroupSelections(AuthorityDataRuleGroupQUERY query) {
        return dataRuleGroupService.getDataRuleGroupSelection(query);
    }

    @ApiOperation("权限数据规则树")
    @RequestMapping(value = "authority-data-rule-tree", method = RequestMethod.GET)
    public List<AuthorityDataRuleGroupTreeNode> getAuthorityDataRuleTree() {
        return dataRuleGroupService.getAuthorityDataRuleGroupTree();
    }

    @ApiOperation(value = "角色的权限数据规则配置", notes = "查询当前角色拥有的权限的数据规则")
    @RequestMapping(value = "checked-authority-data-rule-group", method = RequestMethod.GET)
    public List<AuthorityDataRuleGroupTreeNode> getCheckedAuthorityDataRuleGroupTreeNodes(Integer roleId) {
        return dataRuleGroupService.getCheckedAuthorityDataRuleGroupTreeNodes(roleId);
    }

    @ApiOperation("保存角色的权限数据规则")
    @RequestMapping(value = "role-authority-data-rule-group", method = RequestMethod.POST)
    public void saveRoleAuthorityDataRuleGroup(@RequestBody RoleAuthorityDataRuleGroupSaveDTO dto) {
        dataRuleGroupService.saveRoleAuthorityDataRuleGroup(dto);
    }

}
