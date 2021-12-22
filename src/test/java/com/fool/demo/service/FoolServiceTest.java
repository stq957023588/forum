package com.fool.demo.service;

import com.fool.demo.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class FoolServiceTest {

    private FoolService foolService;

    @Autowired
    public void setFoolService(FoolService foolService) {
        this.foolService = foolService;
    }


    @Test
    void getRandomStudent() throws InterruptedException {
        String key = "唐11";
        for (int i = 0; i < 10; i++) {
            Student randomStudent = foolService.getRandomStudent(key);
            log.info("{}", randomStudent);
            Thread.sleep(1000);
        }
    }
}