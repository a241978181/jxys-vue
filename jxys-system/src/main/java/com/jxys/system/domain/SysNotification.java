package com.jxys.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jxys.common.annotation.Excel;
import com.jxys.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 通知管理对象 sys_notification
 *
 * @author 李建
 * @date 2022-10-09
 */
@TableName(value = "sys_notification")
public class SysNotification extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 通知级别
     */
    @Excel(name = "通知级别")
    private Integer level;

    /**
     * URL
     */
    @Excel(name = "URL")
    private String url;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String message;

    /**
     * 通知状态
     */
    @Excel(name = "通知状态")
    private String status;

    @TableField(exist = false)
    private String usernameArr;
    @TableField(exist = false)
    private List<SysNotificationUser> usernameList;

    public List<SysNotificationUser> getUsernameList() {
        return usernameList;
    }

    public void setUsernameList(List<SysNotificationUser> usernameList) {
        this.usernameList = usernameList;
    }

    public String getUsernameArr() {
        return usernameArr;
    }

    public void setUsernameArr(String usernameArr) {
        this.usernameArr = usernameArr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("deleted", getDeleted())
                .append("level", getLevel())
                .append("url", getUrl())
                .append("title", getTitle())
                .append("message", getMessage())
                .append("status", getStatus())
                .toString();
    }
}

