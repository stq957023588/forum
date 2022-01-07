package com.fool.demo.service;
import com.fool.demo.consts.WhiteListType;
import com.fool.demo.domain.Authority;
import com.fool.demo.domain.Menu;
import com.fool.demo.domain.WhiteList;
import com.fool.demo.entity.WhiteListAddDTO;
import com.fool.demo.entity.WhiteListDTO;
import com.fool.demo.entity.WhiteListQUERY;
import com.fool.demo.mapper.AuthorityMapper;
import com.fool.demo.mapper.MenuMapper;
import com.fool.demo.mapper.WhiteListMapper;
import com.fool.demo.utils.PageUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

/**
 * @author fool
 * @date 2021/12/28 9:30
 */
@Slf4j
@Service
public class WhiteListService {

    private final WhiteListMapper whiteListMapper;

    private final AuthorityMapper authorityMapper;

    private final MenuMapper menuMapper;

    public WhiteListService(WhiteListMapper whiteListMapper, AuthorityMapper authorityMapper, MenuMapper menuMapper) {
        this.whiteListMapper = whiteListMapper;
        this.authorityMapper = authorityMapper;
        this.menuMapper = menuMapper;
    }

    public PageInfo<WhiteListDTO> getWhiteList(WhiteListQUERY query) {
        ISelect select = () -> whiteListMapper.selectByType(query.getType());
        Function<WhiteList, WhiteListDTO> transfer = whiteList -> {
            WhiteListDTO dto = new WhiteListDTO(whiteList.getId());

            if (WhiteListType.AUTHORITY.equals(whiteList.getType())) {
                Authority authority = authorityMapper.selectByPrimaryKey(whiteList.getWhiteId().longValue());
                dto.setPath(authority.getUrl());
                dto.setName(authority.getName());
                dto.setMethod(authority.getMethod());
            } else if (WhiteListType.MENU.equals(whiteList.getType())) {
                Menu menu = menuMapper.selectByPrimaryKey(whiteList.getWhiteId().longValue());
                dto.setPath(menu.getUrl());
                dto.setName(menu.getName());
            }
            return dto;
        };
        return PageUtils.doSelect(select, query, transfer);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(WhiteListAddDTO dto) {
        for (Integer whiteId : dto.getWhiteIdList()) {
            WhiteList exist = whiteListMapper.selectByWhiteIdAndType(whiteId, dto.getType());
            if (exist != null) {
                log.warn("白名单已存在,WhiteListId:{}", exist.getId());
                continue;
            }
            WhiteList whiteList = new WhiteList();
            whiteList.setWhiteId(whiteId);
            whiteList.setType(dto.getType());
            whiteListMapper.insertSelective(whiteList);
        }
    }

    public void delete(List<Integer> idList) {
        if (idList.isEmpty()) {
            log.debug("需要删除的idList为空");
            return;
        }
        whiteListMapper.deleteByIdList(idList);
    }


}
