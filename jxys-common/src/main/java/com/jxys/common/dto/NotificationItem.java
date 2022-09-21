package com.jxys.common.dto;

/**
 * 通知类
 *
 * @Author a2417
 * @Date 2022/9/20
 */
public class NotificationItem {

    /**
     * 消息主键(需要客户端进行反馈时传入，客户端查看后会通过webSocket传送回来)
     */
    private String id;

    /**
     * 标题
     */
    private String title = "系统通知";
    /**
     * 内容
     */
    private String message;
    /**
     * 级别
     */
    private Integer level;

    /**
     * 跳转链接（不填写时前端将跳转打开页面显示message的内容）
     */
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

}
