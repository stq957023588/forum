package com.fool.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@Component
@ServerEndpoint("/demo/radio/{key}")
public class RadioWebSocket {

    @OnOpen
    public void onOpen(Session session, @PathParam("key") String key) {

    }

    @OnClose
    public void onClose(Session session, @PathParam("key") String key) {

    }

    @OnMessage
    public void onMessage(String message, @PathParam("key") String key) {

    }

    @OnError
    public void onError(Throwable throwable, @PathParam("key") String key) {
        log.error("Private char websocket errored",throwable);

    }
}
