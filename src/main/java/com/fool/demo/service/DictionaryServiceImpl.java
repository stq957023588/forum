package com.fool.demo.service;

import com.fool.demo.api.DictionaryService;
import com.fool.demo.domain.Dictionary;
import com.fool.demo.domain.DictionaryType;
import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.DictDTO;
import com.fool.demo.entity.DictQUERY;
import com.fool.demo.entity.DictTypeDTO;
import com.fool.demo.mapper.DictionaryMapper;
import com.fool.demo.mapper.DictionaryTypeMapper;
import com.fool.demo.mapstruct.DictConvertor;
import com.fool.demo.mapstruct.DictTypeConvertor;
import com.fool.demo.utils.Dict;
import com.fool.demo.utils.PageUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * @author fool
 * @date 2022/1/7 10:37
 */
@Service
public class DictionaryServiceImpl implements DictionaryService<Integer, Integer> {

    private final DictionaryMapper dictionaryMapper;

    private final DictionaryTypeMapper dictionaryTypeMapper;

    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper, DictionaryTypeMapper dictionaryTypeMapper) {
        this.dictionaryMapper = dictionaryMapper;
        this.dictionaryTypeMapper = dictionaryTypeMapper;
    }


    public void addDictType(DictTypeDTO dto) {
        DictionaryType dictionaryType = DictTypeConvertor.INSTANCE.toDomain(dto);
        dictionaryTypeMapper.insertSelective(dictionaryType);
    }

    public void updateDictType(DictTypeDTO dto) {
        DictionaryType dictionaryType = DictTypeConvertor.INSTANCE.toDomain(dto);
        dictionaryTypeMapper.updateByPrimaryKeySelective(dictionaryType);
    }

    public void deleteDictType(List<Integer> codes) {
        dictionaryTypeMapper.deleteByPrimaryKeys(codes);
    }

    public PageInfo<DictTypeDTO> getDictType(CommonQUERY query) {
        ISelect select = dictionaryTypeMapper::selectAll;
        return PageUtils.doSelect(select, query, DictTypeConvertor.INSTANCE::toDataTransferObject);
    }


    public void addDict(DictDTO dto) {
        Dictionary dictionary = DictConvertor.INSTANCE.toDomain(dto);

        List<Dictionary> dictionaries = dictionaryMapper.selectByType(dto.getType());
        Integer maxCode = dictionaries.stream().max(Comparator.comparing(Dictionary::getCode)).map(Dictionary::getCode).orElse(0);
        dictionary.setCode(maxCode + 1);
        dictionaryMapper.insertSelective(dictionary);
    }

    public void updateDict(DictDTO dto) {
        Dictionary dictionary = DictConvertor.INSTANCE.toDomain(dto);
        dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDict(List<DictDTO> dictList) {
        for (DictDTO dto : dictList) {
            dictionaryMapper.deleteByPrimaryKey(dto.getType(), dto.getCode());
        }
    }

    public PageInfo<DictDTO> getDict(DictQUERY query) {
        ISelect select = () -> dictionaryMapper.select(query);
        return PageUtils.doSelect(select, query, DictConvertor.INSTANCE::toDataTransferObject);
    }


    @Override
    public Dict<Integer, Integer> translate(Integer type, Integer code) {
        Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(type, code);
        return DictConvertor.INSTANCE.toDataTransferObject(dictionary);
    }
}
