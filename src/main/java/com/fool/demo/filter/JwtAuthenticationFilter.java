package com.fool.demo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fool.demo.entity.CustomizeUser;
import com.fool.demo.property.JwtProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author fool
 * @date 2021/10/21 16:42
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtProperty jwtProperty;

    @Autowired
    public void setJwtProperty(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(jwtProperty.getTokenHeader());

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperty.getSecret());
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwtProperty.getIssuer()).build();

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            DecodedJWT verify = verifier.verify(token);

            int id = verify.getClaim("id").asInt();
            String name = verify.getClaim("name").asString();
            String email = verify.getClaim("email").asString();
            String avatar = verify.getClaim("avatar").asString();
            String[] roles = verify.getClaim("roles").asArray(String.class);

            List<GrantedAuthority> permissions = Stream.of(roles).map(e -> (GrantedAuthority) () -> e).collect(Collectors.toList());

            CustomizeUser user = new CustomizeUser(id, name, avatar, email, "PROTECTED", permissions);

            authentication = new UsernamePasswordAuthenticationToken(user, null, permissions);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            logger.info(String.format("Authenticated user %s, setting security context", email));
        } catch (JWTVerificationException e) {
            logger.warn(String.format("Verify token failed:%s", token), e);
        } catch (Exception e) {
            logger.error(String.format("Parsing token failed:%s", token), e);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
