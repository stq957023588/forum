package com.fool.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fool.demo.entity.CustomizeUser;
import com.fool.demo.entity.UserDTO;
import com.fool.demo.mapstruct.UserConvertor;
import com.fool.demo.property.JwtProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author fool
 * @date 2021/12/20 13:55
 */
public class UserUtils {

    public static UserDTO getCurrentUser() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomizeUser customizeUser = (CustomizeUser) authenticationToken.getPrincipal();
        return UserConvertor.INSTANCE.toDataTransferObject(customizeUser);
    }

    public static CustomizeUser getCurrentUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomizeUser) {
            return (CustomizeUser) principal;
        }
        throw new TokenExpiredException("Token已过期");
    }

    public static UserDTO getUserInfo(String token) {
        if (token == null) {
            return null;
        }

        if (token.startsWith("Bearer ")) {
            token = token.replaceFirst("Bearer ", token);
        }

        JwtProperty jwtProperty = SpringContextUtils.getBean(JwtProperty.class);

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperty.getSecret());
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwtProperty.getIssuer()).build();

            DecodedJWT verify = verifier.verify(token);

            String name = verify.getClaim("name").asString();
            String email = verify.getClaim("email").asString();
            String[] roles = verify.getClaim("roles").asArray(String.class);

            UserDTO user = new UserDTO();
            user.setName(name);
            user.setEmail(email);
            user.setRoles(Stream.of(roles).collect(Collectors.toList()));

            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
