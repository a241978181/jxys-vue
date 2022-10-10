package com.jxys.system.service;


import com.jxys.system.domain.SysNotification;
import com.jxys.system.domain.SysNotificationUser;

import java.util.List;

/**
 * 通知管理Service接口
 *
 * @author 李建
 * @date 2022-10-09
 */
public interface SysNotificationService {
    /**
     * 查询通知管理
     *
     * @param id 通知管理主键
     * @return 通知管理
     */
    public SysNotification selectSysNotificationById(Long id);

    /**
     * 查询通知公告用户列表
     *
     * @param sysNotificationUser 通知公告用户
     * @return 通知公告用户集合
     */
    public List<SysNotificationUser> selectSysNotificationUserList(SysNotificationUser sysNotificationUser);

    /**
     * 查询通知管理列表
     *
     * @param sysNotification 通知管理
     * @return 通知管理集合
     */
    public List<SysNotification> selectSysNotificationList(SysNotification sysNotification);

    /**
     * 新增通知管理
     *
     * @param sysNotification 通知管理
     * @return 结果
     */
    public int insertSysNotification(SysNotification sysNotification);

    /**
     * 修改通知管理
     *
     * @param sysNotification 通知管理
     * @return 结果
     */
    public int updateSysNotification(SysNotification sysNotification);

    /**
     * 批量删除通知管理
     *
     * @param ids 需要删除的通知管理主键集合
     * @return 结果
     */
    public int deleteSysNotificationByIds(Long[] ids);

    /**
     * 删除通知管理信息
     *
     * @param id 通知管理主键
     * @return 结果
     */
    public int deleteSysNotificationById(Long id);

    /**
     * 已读多条通知
     *
     * @param notificationId
     * @param username
     */
    public void read(Long notificationId, String username);

    /**
     * 重新发送多条通知
     *
     * @param username
     */
    public void restartSend(String username);
}

