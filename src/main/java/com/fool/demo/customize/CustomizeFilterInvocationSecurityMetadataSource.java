package com.fool.demo.customize;


import com.fool.demo.domain.Authority;
import com.fool.demo.domain.Role;
import com.fool.demo.mapper.AuthorityMapper;
import com.fool.demo.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author fool
 * @date 2021/10/21 9:28
 */
@Slf4j
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final RoleMapper roleMapper;

    private final AuthorityMapper authorityMapper;

    private Role superRole;

    @Value("${security.super-role}")
    public void setSuperRole(String superRole) {
        this.superRole = new Role();
        this.superRole.setName(superRole);
    }

    public CustomizeFilterInvocationSecurityMetadataSource(RoleMapper roleMapper, AuthorityMapper authorityMapper) {
        this.roleMapper = roleMapper;
        this.authorityMapper = authorityMapper;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        // 获取HTTP请求防范,GET,POST....
        String method = filterInvocation.getRequest().getMethod();
        //获取请求地址
        String requestUrl = filterInvocation.getRequestUrl();
        log.debug("Request url:{},method:{}", requestUrl, method);

        if (requestUrl.contains("?")) {
            int indexOfQuestionMark = requestUrl.indexOf("?");
            requestUrl = requestUrl.substring(0, indexOfQuestionMark);
        }

        List<Authority> authorities = authorityMapper.selectWhiteListByMethod(method);

        boolean intercept = true;

        for (Authority authority: authorities)  {
            Pattern compile = Pattern.compile("^" + authority.getUrl());
            Matcher matcher = compile.matcher(requestUrl);
            if (matcher.find()){
                intercept = false;
                break;
            }
        }
        if (!intercept){
            return new ArrayList<>(0);
        }

        // Authority whiteList = authorityMapper.selectWhiteListByUrlAndMethod(requestUrl, method);
        // if (whiteList != null) {
        //     return new ArrayList<>(0);
        // }

        // 查询拥有该路径权限的角色,
        List<Role> roles = roleMapper.selectRolesByUrlAndMethod(requestUrl, method);
        roles.add(superRole);
        // String[] attributes = {"ADMIN"};
        // return SecurityConfig.createList(attributes);
        return roles.stream().map(Role::getName).map(SecurityConfig::new).collect(Collectors.toList());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


}




