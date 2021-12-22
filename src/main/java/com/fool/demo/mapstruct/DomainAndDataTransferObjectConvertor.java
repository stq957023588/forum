package com.fool.demo.mapstruct;

/**
 * @author fool
 * @date 2021/12/16 16:03
 */
public interface DomainAndDataTransferObjectConvertor<S, T> {

    T toDataTransferObject(S s);

    S toDomain(T t);



}
