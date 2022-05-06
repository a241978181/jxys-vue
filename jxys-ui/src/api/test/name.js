import request from '@/utils/request'

// 查询测试名称列表
export function listName(query) {
  return request({
    url: '/test/name/list',
    method: 'get',
    params: query
  })
}

// 查询测试名称详细
export function getName(id) {
  return request({
    url: '/test/name/' + id,
    method: 'get'
  })
}

// 新增测试名称
export function addName(data) {
  return request({
    url: '/test/name',
    method: 'post',
    data: data
  })
}

// 修改测试名称
export function updateName(data) {
  return request({
    url: '/test/name',
    method: 'put',
    data: data
  })
}

// 删除测试名称
export function delName(id) {
  return request({
    url: '/test/name/' + id,
    method: 'delete'
  })
}

