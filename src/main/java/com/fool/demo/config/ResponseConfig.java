package com.fool.demo.config;

import com.fool.demo.consts.HttpHeaderConst;
import com.fool.demo.entity.DefaultResult;
import com.fool.demo.entity.Result;
import com.fool.demo.enums.RequestHeaderEnum;
import com.fool.demo.utils.SpringContextUtils;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fool
 * @date 2021/10/18 15:59
 */

@ControllerAdvice
public class ResponseConfig implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = SpringContextUtils.getRequest();
        // 从Http请求中获取请求头
        String headerValue = request.getHeader(HttpHeaderConst.UNIFORM_RESPONSE);
        return !RequestHeaderEnum.NON_UNIFORM_RESPONSE.getValue().equals(headerValue);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 从http请求中获取在拦截器中预处理时添加的UniformResponse注解(这里是在ResponseInterceptor中添加的)
        // UniformResponse uniformResponseAnn = (UniformResponse) SpringContextUtils.getRequest().getAttribute(ResponseInterceptor.RESPONSE_RESULT);

        // Class<? extends Result> resultClass = uniformResponseAnn.templateClass();
        if (body instanceof Result) {
            return body;
        }

        Result<Object> result = new DefaultResult<>();
        result.setCode(Result.SUCCESS);
        result.setData(body);

        // if (body instanceof String) {
        //     return JSONObject.toJSONString(result);
        // }


        return result;
    }
}
