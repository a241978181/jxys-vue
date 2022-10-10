package com.jxys.system.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jxys.common.annotation.Excel;
import com.jxys.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 通知公告用户对象 sys_notification_user
 *
 * @author hxlh
 * @date 2022-10-09
 */
@TableName(value = "sys_notification_user")
public class SysNotificationUser extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 通知公告id
     */
    @Excel(name = "通知公告id")
    private Long sysNotificationId;

    /**
     * 通知对象
     */
    @Excel(name = "通知对象")
    private String sysUserName;

    /**
     * 通知状态（0未读 1已读）
     */
    @Excel(name = "通知状态", readConverterExp = "0=未读,1=已读")
    private String status;

    /**
     * 是否发送（0未发送 1已发送）
     */
    @Excel(name = "是否发送", readConverterExp = "0未发送 1已发送")
    private String send;

    /**
     * 通知对象
     */
    @TableField(exist = false)
    private String sysNickName;

    public String getSysNickName() {
        return sysNickName;
    }

    public void setSysNickName(String sysNickName) {
        this.sysNickName = sysNickName;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSysNotificationId() {
        return sysNotificationId;
    }

    public void setSysNotificationId(Long sysNotificationId) {
        this.sysNotificationId = sysNotificationId;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
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
                .append("sysNotificationId", getSysNotificationId())
                .append("sysUserName", getSysUserName())
                .append("status", getStatus())
                .toString();
    }
}

