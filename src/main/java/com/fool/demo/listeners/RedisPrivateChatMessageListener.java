package com.fool.demo.listeners;

import com.alibaba.fastjson.JSONObject;
import com.fool.demo.entity.PrivateChatMessage;
import com.fool.demo.websocket.PrivateChatWebSocket;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;


public class RedisPrivateChatMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        PrivateChatMessage privateChatMessage = JSONObject.parseObject(message.toString()).toJavaObject(PrivateChatMessage.class);
        PrivateChatWebSocket.onRadioMessage(privateChatMessage);
    }
}
