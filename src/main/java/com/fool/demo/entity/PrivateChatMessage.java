package com.fool.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
// @Document("private_chat_message_record")
public class PrivateChatMessage {
    private Long id;

    private String from;

    private String to;

    private String content;

    private Date sendTime;

}
