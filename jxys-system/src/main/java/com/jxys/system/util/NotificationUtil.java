package com.jxys.system.util;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.jxys.common.constant.Constants;
import com.jxys.common.core.redis.RedisCache;
import com.jxys.common.dto.NotificationItem;
import com.jxys.common.utils.StringUtils;
import com.jxys.system.service.SysNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    private static NotificationUtil notificationUtil;
    private SysNotificationService sysNotificationService;

    @PostConstruct
    public void init() {
        notificationUtil = this;
        // 初使化时将已静态化的configParam实例化
        notificationUtil.sysNotificationService = this.sysNotificationService;
    }

    @Autowired
    public void setSysNotificationService(SysNotificationService sysNotificationService) {
        this.sysNotificationService = sysNotificationService;
    }

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
        //调用方法查询是否存在未发送的通知消息
        notificationUtil.sysNotificationService.restartSend(userName);
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
        //判断已读信息
        if (Constants.NOTIFICATION_STATUS_ALL.equals(message)) {
            notificationUtil.sysNotificationService.read(null, userName);
        } else {
            notificationUtil.sysNotificationService.read(Long.parseLong(message), userName);
        }
    }


    /**
     * 发送单一客户端消息
     *
     * @param notificationItem 消息组件
     * @param userName         用户账号
     * @return 成功发送的用户账号
     */
    public String sendMessageOne(NotificationItem notificationItem, String userName) {
        Map<String, Session> sessionMap = clients.get(userName);
        if (sessionMap != null) {
            for (Session value : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}发送级别{}", value.getId(), notificationItem.getMessage(), notificationItem.getLevel());
                value.getAsyncRemote().sendText(JSONUtil.toJsonStr(notificationItem));
            }
            return userName;
        } else {
            return null;
        }
    }

    /**
     * 发送指定用户消息
     *
     * @param notificationItem 消息对象
     * @param userList         用户账号组
     * @return 成功发送的用户账号组
     */
    public List<String> sendMessageList(NotificationItem notificationItem, List<String> userList) {
        List<String> successList = new ArrayList<>();
        if (userList != null && userList.size() > 0) {
            for (String userName : userList) {
                String u = this.sendMessageOne(notificationItem, userName);
                if (StringUtils.isNotBlank(u)) {
                    successList.add(u);
                }
            }
            return successList;
        } else {
            return successList;
        }

    }


}