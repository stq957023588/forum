package com.fool.demo.controller;

import com.fool.demo.entity.WhiteListAddDTO;
import com.fool.demo.entity.WhiteListDTO;
import com.fool.demo.entity.WhiteListQUERY;
import com.fool.demo.service.WhiteListService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/28 9:56
 */
@RestController
public class WhiteListController {

    private final WhiteListService whiteListService;

    public WhiteListController(WhiteListService whiteListService) {
        this.whiteListService = whiteListService;
    }

    @ApiOperation("添加白名单")
    @RequestMapping(value = "white-list", method = RequestMethod.POST)
    public void add(@RequestBody WhiteListAddDTO dto) {
        whiteListService.add(dto);
    }

    @ApiOperation("删除白名单")
    @RequestMapping(value = "white-list", method = RequestMethod.DELETE)
    public void delete(@RequestBody List<Integer> idList) {
        whiteListService.delete(idList);
    }

    @ApiOperation("白名单列表")
    @RequestMapping(value = "white-list", method = RequestMethod.GET)
    public PageInfo<WhiteListDTO> get(WhiteListQUERY query) {
        return whiteListService.getWhiteList(query);
    }

}
