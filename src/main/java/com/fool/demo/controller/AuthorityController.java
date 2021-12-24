package com.fool.demo.controller;

import com.fool.demo.entity.AuthorityDTO;
import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.RoleAuthorityQUERY;
import com.fool.demo.service.AuthorityService;
import com.github.pagehelper.PageInfo;
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

    @RequestMapping(value = "authority", method = RequestMethod.GET)
    public PageInfo<AuthorityDTO> getAuthorities(CommonQUERY query) {
        return authorityService.getAuthorities(query);
    }

    @RequestMapping(value = "role-authority", method = RequestMethod.GET)
    public PageInfo<AuthorityDTO> getRoleAuthority(RoleAuthorityQUERY query) {
        return authorityService.getRoleAuthority(query);
    }

    @RequestMapping(value = "authority", method = RequestMethod.POST)
    public void add(@RequestBody AuthorityDTO dto) {
        authorityService.add(dto);
    }


    @RequestMapping(value = "authority", method = RequestMethod.PUT)
    public void update(@RequestBody AuthorityDTO dto) {
        authorityService.update(dto);
    }


}
