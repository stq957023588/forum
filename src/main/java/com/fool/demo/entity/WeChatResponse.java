package com.fool.demo.entity;

import lombok.Data;

/**
 * @author fool
 * @date 2021/8/12 10:21
 */
@Data
public class WeChatResponse {

    private String errcode;

    private String errmsg;

    private String session_key;

    private String openid;

}
