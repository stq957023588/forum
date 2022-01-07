package com.fool.demo.controller;

import com.fool.demo.service.AsyncTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fool
 * @date 2021/12/14 14:37
 */
@RestController
public class AsyncTestController {

    private final AsyncTestService asyncTestService;

    public AsyncTestController(AsyncTestService asyncTestService) {
        this.asyncTestService = asyncTestService;
    }

    @ApiOperation("异步调用测试")
    @RequestMapping(value = "noneReturnAsync", method = RequestMethod.GET)
    public String noneReturnAsyncTest() {
        asyncTestService.noneReturnAsyncTest5000();
        asyncTestService.noneReturnAsyncTest7000();

        return "SUCCESS";
    }

}

