package com.fool.demo.mapper;

import com.fool.demo.domain.Student;

/**
 * @Entity com.fool.demo.domain.Student
 */
public interface StudentMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

}




