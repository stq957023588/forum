package com.fool.demo.mapper;

import com.fool.demo.domain.DictionaryType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.DictionaryType
 */
@Mapper
public interface DictionaryTypeMapper {

    int deleteByPrimaryKey(Long id);

    int deleteByPrimaryKeys(@Param("primaryKeys")List<Integer> primaryKeys);

    int insert(DictionaryType record);

    int insertSelective(DictionaryType record);

    DictionaryType selectByPrimaryKey(Long id);

    List<DictionaryType> selectAll();

    int updateByPrimaryKeySelective(DictionaryType record);

    int updateByPrimaryKey(DictionaryType record);

}




