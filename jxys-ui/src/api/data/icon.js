import request from '@/utils/request'

// 查询icon图标列表
export function listIcon(query) {
  return request({
    url: '/data/icon/list',
    method: 'get',
    params: query
  })
}

// 查询icon图标详细
export function getIcon(id) {
  return request({
    url: '/data/icon/' + id,
    method: 'get'
  })
}

// 新增icon图标
export function addIcon(data) {
  return request({
    url: '/data/icon',
    method: 'post',
    data: data
  })
}

// 修改icon图标
export function updateIcon(data) {
  return request({
    url: '/data/icon',
    method: 'put',
    data: data
  })
}

// 删除icon图标
export function delIcon(id) {
  return request({
    url: '/data/icon/' + id,
    method: 'delete'
  })
}
