package com.fool.demo.service;

import com.fool.demo.domain.Authority;
import com.fool.demo.entity.AuthorityDTO;
import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.RoleAuthorityQUERY;
import com.fool.demo.mapper.AuthorityMapper;
import com.fool.demo.mapstruct.AuthorityConvertor;
import com.fool.demo.utils.PageUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * @author fool
 * @date 2021/12/24 10:33
 */
@Service
public class AuthorityService {

    private final AuthorityMapper authorityMapper;

    public AuthorityService(AuthorityMapper authorityMapper) {
        this.authorityMapper = authorityMapper;
    }

    public PageInfo<AuthorityDTO> getAuthorities(CommonQUERY query) {
        ISelect select = authorityMapper::selectAll;
        return PageUtils.doSelect(select, query, AuthorityConvertor.INSTANCE::toDataTransferObject);
    }

    public PageInfo<AuthorityDTO> getRoleAuthority(RoleAuthorityQUERY query) {
        ISelect select = () -> authorityMapper.selectByRoleAuthorityParams(query);
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

}
