package com.jxys.common.utils;

import com.alibaba.fastjson2.JSON;
import com.jxys.common.core.redis.RedisCache;
import com.jxys.common.dto.NotificationItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author a2417
 * @Date 2022/9/20
 */
@Slf4j
@ServerEndpoint(value = "/jxys/webSocket/notification")
@Component
public class NotificationUtil {

    /**
     * 记录当前在线连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Map<String, Session>> clients = new ConcurrentHashMap<>();
    @Autowired
    private RedisCache redisCache;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineCount.incrementAndGet(); // 在线数加1
        String userName = session.getRequestParameterMap().get("userName").get(0);
        Map sessionMap = clients.get(userName);
        if (sessionMap == null) {
            sessionMap = new HashMap();
        }
        sessionMap.put(session.getId(), session);
        clients.put(userName, sessionMap);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet(); // 在线数减1
        String userName = session.getRequestParameterMap().get("userName").get(0);
        Map<String, Session> sessionMap = clients.get(userName);
        if (sessionMap != null) {
            for (Session value : sessionMap.values()) {
                if (value.getId().equals(session.getId())) {
                    sessionMap.remove(session.getId());
                }
            }
        }
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String userName = session.getRequestParameterMap().get("userName").get(0);
        log.info("服务端收到客户端[{}]的消息:{}用户为：{}", session.getId(), message, userName);
        //userName为用户账号
        //message为已读内容id
        //后面可以自行设计逻辑修改数据已读标识
    }


    /**
     * 发送单一客户端消息
     *
     * @param notificationItem 消息组件
     * @param userName         用户账号
     * @return 未成功发送的用户账号
     */
    public String sendMessageOne(NotificationItem notificationItem, String userName) {
        Map<String, Session> sessionMap = clients.get(userName);
        if (sessionMap != null) {
            for (Session value : sessionMap.values()) {
                System.out.println(value);
                log.info("服务端给客户端[{}]发送消息{}发送级别{}", value.getId(), notificationItem.getMessage(), notificationItem.getLevel());
                value.getAsyncRemote().sendText(JSON.toJSONString(notificationItem));
            }
            return null;
        } else {
            return userName;
        }
    }

    /**
     * 发送指定用户消息
     *
     * @param notificationItem 消息对象
     * @param userList         用户账号组
     * @return 未成功发送的用户账号组
     */
    public List<String> sendMessageList(NotificationItem notificationItem, List<String> userList) {
        List<String> errorList = new ArrayList<>();
        if (userList != null && userList.size() > 0) {
            for (String userName : userList) {
                String u = this.sendMessageOne(notificationItem, userName);
                if (StringUtils.isNotBlank(u)) {
                    errorList.add(u);
                }
            }
            return errorList;
        } else {
            return errorList;
        }

    }


}
