package com.fool.demo.controller;

import com.fool.demo.entity.AuthorityDTO;
import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.RoleAuthorityDmlDTO;
import com.fool.demo.entity.RoleAuthorityQUERY;
import com.fool.demo.service.AuthorityService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fool
 * @date 2021/12/24 10:30
 */
@RestController
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @ApiOperation(value = "获取权限列表", notes = "可分页,排序")
    @RequestMapping(value = "authority", method = RequestMethod.GET)
    public PageInfo<AuthorityDTO> getAuthorities(CommonQUERY query) {
        return authorityService.getAuthorities(query);
    }

    @ApiOperation(value = "角色权限列表", notes = "根据角色ID分页查询角色权限")
    @RequestMapping(value = "role-authority", method = RequestMethod.GET)
    public PageInfo<AuthorityDTO> getRoleAuthority(RoleAuthorityQUERY query) {
        return authorityService.getRoleAuthority(query);
    }

    @ApiOperation(value = "角色未拥有权限", notes = "查询角色ID未拥有的权限")
    @RequestMapping(value = "role-authority-reverse", method = RequestMethod.GET)
    public PageInfo<AuthorityDTO> getAuthorityThatRoleDontHave(RoleAuthorityQUERY query) {
        return authorityService.getAuthorityThatRoleDontHave(query);
    }

    @ApiOperation(value = "新增角色权限", notes = "新增角色权限")
    @RequestMapping(value = "role-authority", method = RequestMethod.POST)
    public void addRoleAuthority(@RequestBody RoleAuthorityDmlDTO dto) {
        authorityService.addRoleAuthority(dto);
    }

    @ApiOperation(value = "删除角色权限", notes = "物理删除角色权限")
    @RequestMapping(value = "role-authority", method = RequestMethod.DELETE)
    public void deleteRoleAuthority(@RequestBody RoleAuthorityDmlDTO dto) {
        authorityService.deleteRoleAuthority(dto);
    }

    @ApiOperation(value = "新增权限", notes = "新增权限")
    @RequestMapping(value = "authority", method = RequestMethod.POST)
    public void add(@RequestBody AuthorityDTO dto) {
        authorityService.add(dto);
    }


    @ApiOperation(value = "更新权限", notes = "更新权限信息")
    @RequestMapping(value = "authority", method = RequestMethod.PUT)
    public void update(@RequestBody AuthorityDTO dto) {
        authorityService.update(dto);
    }


    @ApiOperation(value = "需要权限的权限", notes = "不是白名单中的权限列表")
    @RequestMapping(value = "limited-authority", method = RequestMethod.GET)
    public PageInfo<AuthorityDTO> getLimitedAuthority(CommonQUERY query) {
        return authorityService.getLimitedAuthority(query);
    }

    @RequestMapping(value = {"init-default-authority", "init-default-authority2"}, method = {RequestMethod.PUT, RequestMethod.GET})
    public void initializeDefaultAuthority() {
        authorityService.initializeDefaultAuthority(true);
    }

}
