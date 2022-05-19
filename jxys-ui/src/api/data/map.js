import request from '@/utils/request'

// 查询字典-地图列表
export function listMap(query) {
  return request({
    url: '/zd/map/list',
    method: 'get',
    params: query
  })
}

// 查询字典-地图详细
export function getMap(id) {
  return request({
    url: '/zd/map/' + id,
    method: 'get'
  })
}

// 新增字典-地图
export function addMap(data) {
  return request({
    url: '/zd/map',
    method: 'post',
    data: data
  })
}

// 修改字典-地图
export function updateMap(data) {
  return request({
    url: '/zd/map',
    method: 'put',
    data: data
  })
}

// 删除字典-地图
export function delMap(id) {
  return request({
    url: '/zd/map/' + id,
    method: 'delete'
  })
}
