package com.fool.demo.customize;


import com.fool.demo.domain.Role;
import com.fool.demo.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fool
 * @date 2021/10/21 9:28
 */
@Slf4j
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final RoleMapper roleMapper;

    public CustomizeFilterInvocationSecurityMetadataSource(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
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

        // 查询拥有该路径权限的角色
        List<Role> roles = roleMapper.selectRolesByUrlAndMethod(requestUrl, method);

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

    public static void main(String[] args) {
        String s = "1234567";

        int index = s.indexOf("9");
        String s2 = s.substring(0, index);
    }

}




