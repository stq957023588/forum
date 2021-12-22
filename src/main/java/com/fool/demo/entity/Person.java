package com.fool.demo.entity;

import lombok.Data;

@Data
// @Document(collection = "person")
public class Person {

    private String id;

    private String name;

    private Integer age;

    private String phone;
}
