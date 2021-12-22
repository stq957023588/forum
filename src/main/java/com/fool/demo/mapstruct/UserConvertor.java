package com.fool.demo.mapstruct;

import com.fool.demo.entity.CustomizeUser;
import com.fool.demo.entity.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fool
 * @date 2021/12/20 14:42
 */
@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);



    @Named("transferRole")//需要起个名字，不然报错,可以与方法名一致，当然也可以不一致
    default List<String> transferRole(Collection<GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
    @Mapping(source = "username", target = "email")
    @Mapping(source = "authorities", target = "roles", qualifiedByName = "transferRole")
    UserDTO toDataTransferObject(CustomizeUser user);

}
