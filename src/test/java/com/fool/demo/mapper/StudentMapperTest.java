package com.fool.demo.mapper;

import com.fool.demo.domain.Student;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Locale;

/**
 * @author fool
 * @date 2021/10/15 13:21
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentMapperTest {

    StudentMapper studentMapper;

    @Autowired
    private void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Test
    void insert() {

        Faker faker = new Faker(new Locale("zh-CN"));

        Student student = new Student();
        student.setName(faker.name().fullName());
        student.setSex(0);
        student.setGrade(0);
        student.setClassNo(0);
        student.setCreateTime(new Date());
        student.setUpdateTime(new Date());
        student.setDeleted(0);

        studentMapper.insertSelective(student);
    }

    @Test
    void selectByPrimaryKey() {
        Student student = studentMapper.selectByPrimaryKey(1L);
        System.out.println(student);
    }
}