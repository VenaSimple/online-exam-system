import request from '../utils/request'

export function getUserList(params) {
  return request.get('/user/list', { params })
}

export function getUserById(id) {
  return request.get(`/user/${id}`)
}

export function getUserInfo() {
  return request.get('/user/info')
}

export function createUser(data) {
  return request.post('/user', data)
}

export function updateUser(data) {
  return request.put('/user', data)
}

export function deleteUser(id) {
  return request.delete(`/user/${id}`)
}

export function changePassword(data) {
  return request.put('/user/changePassword', data)
}

export function resetPassword(id) {
  return request.put(`/user/${id}/resetPassword`)
}

export function updateUserStatus(id, status) {
  return request.put(`/user/${id}/status`, null, { params: { status } })
}
