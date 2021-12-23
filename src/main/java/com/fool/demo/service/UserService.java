package com.fool.demo.service;

import com.fool.demo.domain.Role;
import com.fool.demo.domain.User;
import com.fool.demo.entity.CustomizeUser;
import com.fool.demo.mapper.RoleMapper;
import com.fool.demo.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fool
 * @date 2021/10/20 15:33
 */
@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    public UserService(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.selectByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        List<Role> roles = roleMapper.selectByUserId(user.getId());
        List<GrantedAuthority> auth = roles.stream().map(Role::getName).map(e -> (GrantedAuthority) () -> e).collect(Collectors.toList());

        return new CustomizeUser(user.getId(),user.getName(), username, user.getPassword(), auth);
    }



}
