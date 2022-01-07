package com.fool.demo.service;

import com.fool.demo.domain.Role;
import com.fool.demo.domain.User;
import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.CustomizeUser;
import com.fool.demo.entity.UserAddDTO;
import com.fool.demo.entity.UserDTO;
import com.fool.demo.mapper.RoleMapper;
import com.fool.demo.mapper.UserMapper;
import com.fool.demo.mapstruct.UserConvertor;
import com.fool.demo.utils.PageUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, RoleMapper roleMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private String defaultPassword;

    @Value("${security.default-password}")
    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.selectByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        List<Role> roles = roleMapper.selectByUserId(user.getId());
        List<GrantedAuthority> auth = roles.stream().map(Role::getName).map(e -> (GrantedAuthority) () -> e).collect(Collectors.toList());
        if (auth.isEmpty()) {
            GrantedAuthority authority = () -> "User";
            auth.add(authority);
        }

        return new CustomizeUser(user.getId(), user.getName(), user.getHeadPic(), username, user.getPassword(), auth);
    }

    public void add(UserAddDTO dto) {
        User exist = userMapper.selectByEmail(dto.getEmail());
        if (exist != null) {
            throw new RuntimeException("该邮箱已被使用");
        }

        User user = UserConvertor.INSTANCE.toDomain(dto);
        String encodePassword = passwordEncoder.encode(defaultPassword);
        user.setPassword(encodePassword);
        userMapper.insertSelective(user);
    }

    public PageInfo<UserDTO> getUserList(CommonQUERY query) {
        ISelect select = userMapper::selectAll;
        return PageUtils.doSelect(select, query, e -> UserConvertor.INSTANCE.toDataTransferObject((User) e));
    }

}
