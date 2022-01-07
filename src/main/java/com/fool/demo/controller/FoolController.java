package com.fool.demo.controller;

import com.fool.demo.domain.Student;
import com.fool.demo.service.FoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "Fool测试")
@RestController
public class FoolController {

    private FoolService foolService;

    @Autowired
    public void setFoolService(FoolService foolService) {
        this.foolService = foolService;
    }

    @RequestMapping("upper-case-entity")
    public UpperCase getUpperCaseEntity() {
        UpperCase upperCase = new UpperCase();
        upperCase.setFOOL("assads");
        return upperCase;
    }


    @ApiOperation("返回学生")
    @RequestMapping(value = "student", method = RequestMethod.POST)
    public Student getStudent(@RequestBody Student student) {
        return student;
    }


    @ApiOperation("随机学生,Redis 5秒换粗")
    @RequestMapping(value = "random-student", method = RequestMethod.GET)
    public Student getRandomStudent(String name) {
        return foolService.getRandomStudent(name);
    }

    @ApiOperation("学生等级")
    @RequestMapping(value = "rank-student", method = RequestMethod.GET)
    public String getRank(Student student) {
        log.info("Student:{}", student);
        return student.getName();
    }

}
