package com.fool.demo.mapper;

import com.fool.demo.annotation.DataAuthority;
import com.fool.demo.domain.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.Post
 */
@Mapper
public interface PostMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Long id);

    @DataAuthority
    List<Post> selectAll();

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);

}




