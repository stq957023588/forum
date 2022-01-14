package com.fool.demo.mapstruct;

import com.fool.demo.domain.Post;
import com.fool.demo.entity.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2022/1/13 13:57
 */
@Mapper
public interface PostConverter extends DomainAndDataTransferObjectConvertor<Post, PostDTO> {

    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

}
