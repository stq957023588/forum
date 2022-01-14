package com.fool.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author fool
 * @date 2022/1/12 10:10
 */
@Component
@Slf4j
@Order(1)
public class SecondFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Second filter");
        chain.doFilter(request, response);
    }

}
