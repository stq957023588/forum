package com.fool.demo.service;

import com.fool.demo.domain.Authority;
import com.fool.demo.domain.Role;
import com.fool.demo.entity.AuthorityDTO;
import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.RoleAuthorityDmlDTO;
import com.fool.demo.entity.RoleAuthorityQUERY;
import com.fool.demo.mapper.AuthorityMapper;
import com.fool.demo.mapper.RoleMapper;
import com.fool.demo.mapstruct.AuthorityConvertor;
import com.fool.demo.utils.PageUtils;
import com.fool.demo.utils.SpringContextUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fool
 * @date 2021/12/24 10:33
 */
@Slf4j
@Service
public class AuthorityService {

    private final AuthorityMapper authorityMapper;

    private final RoleMapper roleMapper;

    private Role superRole;

    @Value("${security.super-role}")
    public void setSuperRole(String superRole) {
        this.superRole = new Role(superRole);
    }


    public AuthorityService(AuthorityMapper authorityMapper, RoleMapper roleMapper) {
        this.authorityMapper = authorityMapper;
        this.roleMapper = roleMapper;
    }

    public PageInfo<AuthorityDTO> getAuthorities(CommonQUERY query) {
        ISelect select = authorityMapper::selectAll;
        return PageUtils.doSelect(select, query, AuthorityConvertor.INSTANCE::toDataTransferObject);
    }

    public PageInfo<AuthorityDTO> getLimitedAuthority(CommonQUERY query){
     ISelect select = authorityMapper::selectLimitedAuthority;
        return PageUtils.doSelect(select, query, AuthorityConvertor.INSTANCE::toDataTransferObject);
    }

    public PageInfo<AuthorityDTO> getRoleAuthority(RoleAuthorityQUERY query) {
        Role role = roleMapper.selectByPrimaryKey(query.getRoleId().longValue());
        Assert.notNull(role, "角色不存在");
        ISelect select = role.getName().equals(superRole.getName()) ? authorityMapper::selectAll : () -> authorityMapper.selectByRoleAuthorityParams(query);
        return PageUtils.doSelect(select, query, AuthorityConvertor.INSTANCE::toDataTransferObject);
    }

    public PageInfo<AuthorityDTO> getAuthorityThatRoleDontHave(RoleAuthorityQUERY query) {
        Role role = roleMapper.selectByPrimaryKey(query.getRoleId().longValue());
        Assert.notNull(role, "角色不存在");
        if (role.getName().equals(superRole.getName())) {
            return new PageInfo<>();
        }
        ISelect select = () -> authorityMapper.selectRoleDontHaveAuthority(query);
        return PageUtils.doSelect(select, query, AuthorityConvertor.INSTANCE::toDataTransferObject);
    }

    public void add(AuthorityDTO authority) {
        Authority exist = authorityMapper.selectByUrlAndMethod(authority.getUrl(), authority.getMethod());
        if (exist != null) {
            throw new RuntimeException("same url existed");
        }

        Authority auth = AuthorityConvertor.INSTANCE.toDomain(authority);
        authorityMapper.insertSelective(auth);
    }

    public void update(AuthorityDTO auth) {
        Authority exist = authorityMapper.selectByUrlAndMethodAndNotEqualsId(auth.getUrl(), auth.getMethod(), auth.getId());
        if (exist != null) {
            throw new RuntimeException("same url existed");
        }
        Authority origin = authorityMapper.selectByPrimaryKey(auth.getId().longValue());
        Authority update = AuthorityConvertor.INSTANCE.toDomain(auth, origin);

        authorityMapper.updateByPrimaryKey(update);

    }

    @Transactional(rollbackFor = Exception.class)
    public void addRoleAuthority(RoleAuthorityDmlDTO dto) {
        for (AuthorityDTO authority : dto.getAuthorities()) {
            authorityMapper.insertRoleAuthority(dto.getRoleId(), authority.getId());
        }
    }

    public void deleteRoleAuthority(RoleAuthorityDmlDTO dto) {
        Role role = roleMapper.selectByPrimaryKey(dto.getRoleId().longValue());
        Assert.notNull(role, "角色不存在");
        Assert.state(!role.getName().equals(superRole.getName()), "无法删除超级管理员权限");
        List<Integer> authorityIds = dto.getAuthorities().stream().map(AuthorityDTO::getId).collect(Collectors.toList());
        authorityMapper.deleteRoleAuthority(dto.getRoleId(), authorityIds);
    }

    @Async("namedThreadPool")
    public void initializeDefaultAuthority(boolean initDescription) {
        log.info("初始化默认权限");
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RestController.class);
        for (String beanName : beans.keySet()) {
            Class<?> beanClass = beans.get(beanName).getClass();
            Method[] methods = beanClass.getMethods();
            for (Method method : methods) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                if (requestMapping == null || apiOperation == null) {
                    continue;
                }
                String name = apiOperation.value();
                String description = apiOperation.notes();

                int authorityIndex = 1;
                for (String path : requestMapping.value()) {
                    for (RequestMethod requestMethod : requestMapping.method()) {
                        Authority authority = new Authority();
                        authority.setName(authorityIndex == 1 ? name : (name + '-' + authorityIndex));
                        authority.setDescription(description);
                        authority.setUrl("/" + path);
                        authority.setMethod(requestMethod.toString());
                        authority.setCreator(1);
                        authorityIndex++;

                        Authority exist = authorityMapper.selectByUrlAndMethod(authority.getUrl(), authority.getMethod());
                        try {
                            if (exist == null) {
                                authorityMapper.insertSelective(authority);
                                log.info("添加权限 名称:{},路径:{},方法:{},描述:{}", authority.getName(), authority.getUrl(), authority.getMethod(), authority.getDescription());
                                continue;
                            }

                            if (initDescription) {
                                exist.setName(authority.getName());
                                exist.setDescription(authority.getDescription());
                                authorityMapper.updateByPrimaryKey(exist);
                                log.info("更新权限 名称:{},路径:{},方法:{},描述:{}", authority.getName(), authority.getUrl(), authority.getMethod(), authority.getDescription());
                            }
                        } catch (Exception e) {
                            log.info("初始化权限异常 名称:{},路径:{},方法:{},描述:{}", authority.getName(), authority.getUrl(), authority.getMethod(), authority.getDescription(), e);
                        }
                    }
                }
            }
        }

    }

}
