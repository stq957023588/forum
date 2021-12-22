package com.fool.demo.websocket;

import com.alibaba.fastjson.JSONObject;
import com.fool.demo.consts.RedisIncrKey;
import com.fool.demo.consts.RedisRadioChannel;
import com.fool.demo.entity.PrivateChatMessage;
import com.fool.demo.utils.RedisUtils;
import com.fool.demo.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/demo/private-chat/{key}")
public class PrivateChatWebSocket {

    private static final ConcurrentHashMap<String, Session> SESSION_SHELF = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("key") String key) {

        SESSION_SHELF.put(key, session);


    }

    @OnClose
    public void onClose(Session session, @PathParam("key") String key) {
        SESSION_SHELF.remove(key);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("key") String key) {
        log.info("Accept message:{} from {}", message, key);
        try {
            Long incr = RedisUtils.getIncr(RedisIncrKey.PRIVATE_CHAT_ID);

            PrivateChatMessage privateChatMessage = JSONObject.parseObject(message).toJavaObject(PrivateChatMessage.class);
            privateChatMessage.setId(incr);

            // TODO 存入数据库
            // MongoTemplate mongoTemplate = SpringContextUtils.getBean(MongoTemplate.class);
            // mongoTemplate.save(privateChatMessage);

            // 接收到消息,发送广播,通知对应的Session向前端发送消息
            StringRedisTemplate template = SpringContextUtils.getBean(StringRedisTemplate.class);
            template.convertAndSend(RedisRadioChannel.PRIVATE_CHAT_CHANNEL, JSONObject.toJSONString(privateChatMessage));
        } catch (Exception e) {
            log.error("获取信息异常", e);
            SESSION_SHELF.get(key).getAsyncRemote().sendText("获取信息异常:" + e.getMessage());
        }
    }

    public static void onRadioMessage(PrivateChatMessage message) {
        // 接收到Redis广播,查询Session池中是否存在对应的Session
        Session session = SESSION_SHELF.get(message.getTo());
        if (session == null) {
            // 不存在什么都不做
            return;
        }
        // 存在就发送消息
        session.getAsyncRemote().sendText(message.getContent());
        // 更新已读消息ID
        RedisTemplate bean = SpringContextUtils.getBean(RedisTemplate.class);
        bean.opsForValue().set("latest-read-message-id::" + message.getTo() + "::" + message.getFrom(), message.getId());

    }

    @OnError
    public void onError(Throwable throwable, @PathParam("key") String key) {
        log.error("Private char websocket errored", throwable);
        SESSION_SHELF.remove(key);
    }

}
