package com.jxys.web.socket;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author a2417
 * @Date 2022/7/6
 */
@Slf4j
@ServerEndpoint(value = "/jxys/webSocket")
@Component
public class JxysSocket {

    /**
     * 记录当前在线连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Map<String, Session>> clients = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineCount.incrementAndGet(); // 在线数加1
        String equipmentCode = session.getRequestParameterMap().get("type").get(0);
        Map sessionMap = clients.get("equipmentCode");
        if (sessionMap == null) {
            sessionMap = new HashMap();
        }
        sessionMap.put(session.getId(), session);
        clients.put(equipmentCode, sessionMap);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet(); // 在线数减1
        String equipmentCode = session.getRequestParameterMap().get("type").get(0);
        Map<String, Session> sessionMap = clients.get(equipmentCode);
        for (Session value : sessionMap.values()) {
            if (value.getId().equals(session.getId())) {
                sessionMap.remove(session.getId());
            }
        }

        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        String equipmentCode = session.getRequestParameterMap().get("type").get(0);
        //收到消息后两秒钟发送回去
        try {
            Thread.sleep(2000);
            this.sendMessage(message,equipmentCode);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 群发消息
     *
     * @param message 消息内容
     */
    public void sendMessage(String message, String equipmentCode) {
        Map<String, Session> sessionMap = clients.get(equipmentCode);
        for (Session value : sessionMap.values()) {
            log.info("服务端给客户端[{}]发送消息{}", value.getId(), message);
            value.getAsyncRemote().sendText(new Gson().toJson(message));
        }
    }
}
