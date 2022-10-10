package com.jxys.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jxys.common.constant.Constants;
import com.jxys.common.core.domain.entity.SysUser;
import com.jxys.common.core.redis.RedisCache;
import com.jxys.common.dto.NotificationItem;
import com.jxys.system.util.NotificationUtil;
import com.jxys.common.utils.StringUtils;
import com.jxys.system.domain.SysNotification;
import com.jxys.system.domain.SysNotificationUser;
import com.jxys.system.mapper.SysNotificationMapper;
import com.jxys.system.mapper.SysNotificationUserMapper;
import com.jxys.system.mapper.SysUserMapper;
import com.jxys.system.service.SysNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知管理Service业务层处理
 *
 * @author 李建
 * @date 2022-10-09
 */
@Service
public class SysNotificationServiceImpl implements SysNotificationService {
    @Autowired
    private SysNotificationMapper sysNotificationMapper;
    @Resource
    private SysNotificationUserMapper sysNotificationUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisCache redisCache;
    @Resource
    private NotificationUtil notificationUtil;

    /**
     * 查询通知管理
     *
     * @param id 通知管理主键
     * @return 通知管理
     */
    @Override
    public SysNotification selectSysNotificationById(Long id) {
        SysNotification sysNotification = sysNotificationMapper.selectById(id);
        QueryWrapper<SysNotificationUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysNotificationUser::getSysNotificationId, sysNotification.getId());
        List<SysNotificationUser> list = this.sysNotificationUserMapper.selectList(queryWrapper);
        sysNotification.setUsernameList(list);
        return sysNotification;
    }

    /**
     * 查询通知管理列表
     *
     * @param sysNotification 通知管理
     * @return 通知管理
     */
    @Override
    public List<SysNotification> selectSysNotificationList(SysNotification sysNotification) {

        //查询参数
        QueryWrapper<SysNotification> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().orderByDesc(SysNotification::getCreateTime);

        if (StringUtils.isNotNull(sysNotification.getLevel())) {
            queryWrapper.eq("level", sysNotification.getLevel());

        }
        if (StringUtils.isNotNull(sysNotification.getTitle()) && StringUtils.isNotEmpty(sysNotification.getTitle())) {
            queryWrapper.like("title", sysNotification.getTitle());
        }
        if (StringUtils.isNotNull(sysNotification.getStatus()) && StringUtils.isNotEmpty(sysNotification.getStatus())) {
            queryWrapper.eq("status", sysNotification.getStatus());

        }
        //查询数据
        List<SysNotification> list = sysNotificationMapper.selectList(queryWrapper);

        return list;
    }

    /**
     * 查询通知公告用户列表
     *
     * @param sysNotificationUser 通知公告用户
     * @return 通知公告用户
     */
    @Override
    public List<SysNotificationUser> selectSysNotificationUserList(SysNotificationUser sysNotificationUser) {

        //查询参数
        QueryWrapper<SysNotificationUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().orderByDesc(SysNotificationUser::getCreateTime);

        if (StringUtils.isNotNull(sysNotificationUser.getSysNotificationId())) {
            queryWrapper.eq("sys_notification_id", sysNotificationUser.getSysNotificationId());

        }
        if (StringUtils.isNotNull(sysNotificationUser.getSysUserName()) && StringUtils.isNotEmpty(sysNotificationUser.getSysUserName())) {
            queryWrapper.like("sys_user_name", sysNotificationUser.getSysUserName());
        }
        if (StringUtils.isNotNull(sysNotificationUser.getStatus()) && StringUtils.isNotEmpty(sysNotificationUser.getStatus())) {
            queryWrapper.eq("status", sysNotificationUser.getStatus());

        }
        if (StringUtils.isNotNull(sysNotificationUser.getSend()) && StringUtils.isNotEmpty(sysNotificationUser.getSend())) {
            queryWrapper.eq("send", sysNotificationUser.getSend());

        }

        //查询数据
        List<SysNotificationUser> list = sysNotificationUserMapper.selectList(queryWrapper);
        //查询人员名称
        list.stream().peek(obj -> obj.setSysNickName(this.sysUserMapper.selectNickUserByUserName(obj.getSysUserName()))).collect(Collectors.toList());
        return list;
    }

    /**
     * 新增通知管理
     *
     * @param sysNotification 通知管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSysNotification(SysNotification sysNotification) {
        //插入数据库
        int res = sysNotificationMapper.insert(sysNotification);
        //设置通知人员
        this.setSysNotificationUser(sysNotification);
        return res;
    }


    /**
     * 修改通知管理
     *
     * @param sysNotification 通知管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSysNotification(SysNotification sysNotification) {
        LambdaUpdateWrapper<SysNotification> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(SysNotification::getId, sysNotification.getId());
        int res = sysNotificationMapper.update(sysNotification, lambdaUpdateWrapper);
        //删除
        this.deleteSysNotificationUser(sysNotification.getId());
        //设置通知人员
        this.setSysNotificationUser(sysNotification);
        return res;
    }

    /**
     * 批量删除通知管理
     *
     * @param ids 需要删除的通知管理主键
     * @return 结果
     */
    @Override
    public int deleteSysNotificationByIds(Long[] ids) {

        return sysNotificationMapper.deleteBatchIds(java.util.Arrays.asList(ids));
    }

    /**
     * 删除通知管理信息
     *
     * @param id 通知管理主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSysNotificationById(Long id) {
        //删除
        this.deleteSysNotificationUser(id);
        return sysNotificationMapper.deleteById(id);
    }

    /**
     * 已读某条通知
     *
     * @param notificationId
     * @param username
     */
    @Override
    public void read(Long notificationId, String username) {
        LambdaUpdateWrapper<SysNotificationUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        if (notificationId != null) {
            lambdaUpdateWrapper.eq(SysNotificationUser::getSysNotificationId, notificationId);
        }
        lambdaUpdateWrapper.eq(SysNotificationUser::getSysUserName, username);
        lambdaUpdateWrapper.set(SysNotificationUser::getStatus, Constants.NOTIFICATION_STATUS_SUCCESS);
        this.sysNotificationUserMapper.update(null, lambdaUpdateWrapper);
    }

    /**
     * 重新发送多条通知
     *
     * @param username
     */
    @Override
    public void restartSend(String username) {
        QueryWrapper<SysNotificationUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysNotificationUser::getStatus, Constants.NOTIFICATION_STATUS_FAIL);
        queryWrapper.lambda().eq(SysNotificationUser::getSysUserName, username);
        List<SysNotificationUser> sysNotificationUserList = this.sysNotificationUserMapper.selectList(queryWrapper);
        sysNotificationUserList.stream().peek(sysNotificationUser -> {
            QueryWrapper<SysNotification> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SysNotification::getId, sysNotificationUser.getSysNotificationId());
            wrapper.lambda().eq(SysNotification::getStatus, Constants.SUCCESS);
            SysNotification sysNotification = this.sysNotificationMapper.selectOne(wrapper);
            if (sysNotification != null) {
                //发送websocket
                this.sendWeb(sysNotification, sysNotificationUser);
            }
        }).collect(Collectors.toList());
    }

    /**
     * 根据不同人员组设置通知人员
     */
    private void setSysNotificationUser(SysNotification sysNotification) {
        //判断是否携带了用户组，携带便发给该用户组，未携带则发送给全部用户
        if (StringUtils.isNotBlank(sysNotification.getUsernameArr())) {
            String[] usernames = sysNotification.getUsernameArr().split(",");
            for (String username : usernames) {
                SysNotificationUser sysNotificationUser = new SysNotificationUser();
                sysNotificationUser.setSysNotificationId(sysNotification.getId());
                sysNotificationUser.setSysUserName(username);
                sysNotificationUser.setStatus(Constants.NOTIFICATION_STATUS_FAIL);
                sysNotificationUser.setSend(Constants.NOTIFICATION_SEND_FAIL);
                sysNotificationUserMapper.insert(sysNotificationUser);
                //发送websocket
                this.sendWeb(sysNotification, sysNotificationUser);
            }
        } else {
            List<SysUser> list = this.sysUserMapper.selectUserListAll();
            list.stream().peek(sysUser -> {
                SysNotificationUser sysNotificationUser = new SysNotificationUser();
                sysNotificationUser.setSysNotificationId(sysNotification.getId());
                sysNotificationUser.setSysUserName(sysUser.getUserName());
                sysNotificationUser.setStatus(Constants.NOTIFICATION_STATUS_FAIL);
                sysNotificationUser.setSend(Constants.NOTIFICATION_SEND_FAIL);
                sysNotificationUserMapper.insert(sysNotificationUser);
                //发送websocket
                this.sendWeb(sysNotification, sysNotificationUser);
            }).collect(Collectors.toList());
        }
    }

    /**
     * 删除某一个通知的通知人员组
     */
    private void deleteSysNotificationUser(Long id) {
        QueryWrapper<SysNotificationUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysNotificationUser::getSysNotificationId, id);
        this.sysNotificationUserMapper.delete(queryWrapper);
    }

    /**
     * 发送到客户端一条通知并记录
     */
    private void sendWeb(SysNotification sysNotification, SysNotificationUser sysNotificationUser) {
        //打包消息体
        NotificationItem notificationItem = new NotificationItem();
        notificationItem.setTitle(sysNotification.getTitle());
        notificationItem.setLevel(sysNotification.getLevel());
        notificationItem.setMessage(sysNotification.getMessage());
        notificationItem.setUrl(sysNotification.getUrl());
        notificationItem.setId(sysNotification.getId().toString());
        //发送
        if (notificationUtil.sendMessageOne(notificationItem, sysNotificationUser.getSysUserName()) != null) {
            LambdaUpdateWrapper<SysNotificationUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(SysNotificationUser::getId, sysNotificationUser.getId());
            lambdaUpdateWrapper.set(SysNotificationUser::getSend, Constants.NOTIFICATION_SEND_SUCCESS);
            this.sysNotificationUserMapper.update(null, lambdaUpdateWrapper);
        }
    }
}

