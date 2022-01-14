package com.fool.demo.service;

import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.PostDTO;
import com.fool.demo.mapper.PostMapper;
import com.fool.demo.mapstruct.PostConverter;
import com.fool.demo.utils.PageUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * @author fool
 * @date 2022/1/13 13:54
 */
@Service
public class PostService {

    private final PostMapper postMapper;

    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public PageInfo<PostDTO> getPost(CommonQUERY query) {
        ISelect select = postMapper::selectAll;
        return PageUtils.doSelect(select, query, PostConverter.INSTANCE::toDataTransferObject);
    }

}
