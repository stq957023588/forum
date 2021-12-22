package com.fool.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fool
 * @date 2021/10/18 13:59
 */
@RestController
public class HelloWorldController {

    @RequestMapping("hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @RequestMapping("empty")
    public void empty() {
        // int i = 1 / 0;
    }
}
