package com.fool.demo.controller;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fool.demo.entity.WeChatResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fool
 * @date 2021/8/12 10:15
 */
@Api(tags = "微信接口")
@Slf4j
@RestController
public class WeChatController {

    private static final String WECHAT_REQUEST_URI = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}";

    private static final String APP_ID = "";

    private static final String SECRET = "";

    private static final String GRANT_TYPE = "";

    /**
     * 从微信小程序进行调用
     *
     * @param code wx.request获取的code
     * @return session_key 和 openId
     */
    @ApiOperation("获取微信小程序的OpenId和SessionKey")
    @RequestMapping(value = "require-openid", method = RequestMethod.GET)
    public WeChatResponse requireOpenId(String code) {
        RestTemplate restTemplate = new RestTemplate();


        Map<String, String> params = new HashMap<>(4);
        params.put("appid", APP_ID);
        params.put("secret", SECRET);
        params.put("js_code", code);
        params.put("grant_type", GRANT_TYPE);

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(new FastJsonHttpMessageConverter());

        ResponseEntity<WeChatResponse> forEntity = restTemplate.getForEntity(WECHAT_REQUEST_URI, WeChatResponse.class, params);
        // log.info("Response:{}",forEntity);
        return forEntity.getBody();
    }

}
