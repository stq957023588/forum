package com.fool.demo.customize;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author fool
 * @date 2021/12/17 16:02
 */
public class CustomizeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        try {
            ServletInputStream inputStream = request.getInputStream();
            String requestBodyStr = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            JSONObject requestBodyJson = JSONObject.parseObject(requestBodyStr);
            Optional<JSONObject> requestBodyJsonOpt = Optional.ofNullable(requestBodyJson);
            username = username.isEmpty() ? requestBodyJsonOpt.map(e -> e.getString(getUsernameParameter())).orElse("") : username;
            password = password.isEmpty() ? requestBodyJsonOpt.map(e -> e.getString(getPasswordParameter())).orElse("") : password;
        } catch (IOException e) {
            e.printStackTrace();
        }


        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
