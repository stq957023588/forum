package com.fool.demo.mapper;

import com.fool.demo.domain.WhiteList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.WhiteList
 */
@Mapper
public interface WhiteListMapper {

    int deleteByPrimaryKey(Long id);

    int deleteByIdList(@Param("idList") List<Integer> idList);

    int insert(WhiteList record);

    int insertSelective(WhiteList record);

    WhiteList selectByPrimaryKey(Long id);

    List<WhiteList> selectByType(@Param("type")String type) ;

    int updateByPrimaryKeySelective(WhiteList record);

    int updateByPrimaryKey(WhiteList record);

    WhiteList selectByWhiteIdAndType(@Param("whiteId")Integer whiteId,@Param("type")String type);

}




