package com.fool.demo.api;

import com.fool.demo.utils.Dict;

/**
 * @author fool
 * @date 2022/1/7 11:07
 */
public interface DictionaryService<Type, Code> {

    Dict<Type, Code> translate(Type type, Code code);

}
