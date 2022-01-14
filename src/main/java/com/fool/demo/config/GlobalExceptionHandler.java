package com.fool.demo.config;

import com.fool.demo.entity.DefaultResult;
import com.fool.demo.entity.ErrorResult;
import com.fool.demo.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fool
 * @date 2021/10/19 16:16
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public DefaultResult<Object> handleException(Exception e, HttpServletRequest request) {
        log.error("Catch exception:{}.Ip:{},Path:{}", e.getMessage(), IpUtils.getIpAddr(request), request.getServletPath(), e);
        return DefaultResult.error(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult<Object> handleNoHandlerFoundException(Exception e, HttpServletRequest request) {
        log.error("404 Not Found:{}.Ip:{},Path:{}", e.getMessage(), IpUtils.getIpAddr(request), request.getServletPath(), e);
        return  new ErrorResult<>(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(),request.getServletPath());
    }

}
