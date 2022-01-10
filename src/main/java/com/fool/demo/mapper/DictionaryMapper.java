package com.fool.demo.mapper;

import com.fool.demo.domain.Dictionary;
import com.fool.demo.entity.DictQUERY;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.Dictionary
 */
@Mapper
public interface DictionaryMapper {

    int deleteByPrimaryKey(@Param("type")Integer type, @Param("code")Integer code);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(@Param("type")Integer type, @Param("code")Integer code);

    List<Dictionary> selectByType(@Param("type") Integer type);

    List<Dictionary> select(DictQUERY query);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

}




