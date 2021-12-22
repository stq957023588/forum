package com.fool.demo.service;

import com.fool.demo.domain.Student;
import com.fool.redis.CacheExpire;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class FoolService {

    @Cacheable(value = "student", key = "#name" ,unless = "#name == null")
    @CacheExpire(expire = 1000L * 5)
    public Student getRandomStudent(String name) {
        Student student = new Student();
        student.setName(UUID.randomUUID().toString());
        Random random = new Random();
        // student.setAge(random.nextInt());
        // student.setClassNo(random.nextInt());
        return student;
    }

}
