import request from '@/utils/request'

// 查询通知管理列表
export function listNotification(query) {
  return request({
    url: '/system/notification/list',
    method: 'get',
    params: query
  })
}

// 查询通知管理列表
export function listNotificationUser(query) {
  return request({
    url: '/system/notification/userList',
    method: 'get',
    params: query
  })
}

// 查询通知管理详细
export function getNotification(id) {
  return request({
    url: '/system/notification/' + id,
    method: 'get'
  })
}

// 新增通知管理
export function addNotification(data) {
  return request({
    url: '/system/notification',
    method: 'post',
    data: data
  })
}

// 修改通知管理
export function updateNotification(data) {
  return request({
    url: '/system/notification',
    method: 'put',
    data: data
  })
}

// 删除通知管理
export function delNotification(id) {
  return request({
    url: '/system/notification/' + id,
    method: 'delete'
  })
}
