package com.fool.demo.controller;

import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.PostDTO;
import com.fool.demo.service.PostService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fool
 * @date 2022/1/13 13:58
 */
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation("贴子")
    @RequestMapping(value = "post", method = RequestMethod.GET)
    public PageInfo<PostDTO> getPost(CommonQUERY query) {
        return postService.getPost(query);
    }

}
