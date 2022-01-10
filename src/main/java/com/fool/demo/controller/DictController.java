package com.fool.demo.controller;

import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.DictDTO;
import com.fool.demo.entity.DictQUERY;
import com.fool.demo.entity.DictTypeDTO;
import com.fool.demo.service.DictionaryServiceImpl;
import com.fool.demo.utils.Dict;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fool
 * @date 2022/1/7 11:34
 */
@RestController
public class DictController {
    private final DictionaryServiceImpl dictionaryService;

    public DictController(DictionaryServiceImpl dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @ApiOperation("添加字典类型")
    @RequestMapping(value = "dict-type", method = RequestMethod.POST)
    public void addDictType(@RequestBody DictTypeDTO dto) {
        dictionaryService.addDictType(dto);
    }

    @ApiOperation("修改字典类型")
    @RequestMapping(value = "dict-type", method = RequestMethod.PUT)
    public void updateDictType(@RequestBody DictTypeDTO dto) {
        dictionaryService.updateDictType(dto);
    }

    @ApiOperation("删除字典类型")
    @RequestMapping(value = "dict-type", method = RequestMethod.DELETE)
    public void deleteDictType(@RequestBody List<Integer> codes) {
        dictionaryService.deleteDictType(codes);
    }

    @ApiOperation("字典类型")
    @RequestMapping(value = "dict-type", method = RequestMethod.GET)
    public PageInfo<DictTypeDTO> getDictTypes(CommonQUERY query) {
        return dictionaryService.getDictType(query);
    }

    @ApiOperation("添加字典")
    @RequestMapping(value = "dict", method = RequestMethod.POST)
    public void addDict(@RequestBody DictDTO dto) {
        dictionaryService.addDict(dto);
    }

    @ApiOperation("修改字典")
    @RequestMapping(value = "dict", method = RequestMethod.PUT)
    public void updateDict(@RequestBody DictDTO dto) {
        dictionaryService.updateDict(dto);
    }

    @ApiOperation("删除字典")
    @RequestMapping(value = "dict", method = RequestMethod.DELETE)
    public void deleteDict(@RequestBody List<DictDTO> dictList) {
        dictionaryService.deleteDict(dictList);
    }

    @ApiOperation("根据字典类型查询字典")
    @RequestMapping(value = "dict", method = RequestMethod.GET)
    public PageInfo<DictDTO> getDict(DictQUERY query) {
        return dictionaryService.getDict(query);
    }

    @ApiOperation("翻译")
    @RequestMapping(value = "dict/translate", method = RequestMethod.GET)
    public Dict<Integer, Integer> translate(Integer type, Integer code) {
        return dictionaryService.translate(type, code);
    }

}
