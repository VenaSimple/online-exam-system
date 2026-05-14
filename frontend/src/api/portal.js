import request from '../utils/request'

export function getPortalBanners() {
  return request.get('/public/banners')
}
export function getPortalCourses(params) {
  return request.get('/public/courses', { params })
}
export function getPortalNews(params) {
  return request.get('/public/news', { params })
}
export function getPortalNotices(params) {
  return request.get('/public/notices', { params })
}
export function getBannerList() {
  return request.get('/banner/list')
}
export function createBanner(data) {
  return request.post('/banner', data)
}
export function updateBanner(data) {
  return request.put('/banner', data)
}
export function deleteBanner(id) {
  return request.delete(`/banner/${id}`)
}
export function getMyCourses(params) {
  return request.get('/student-course/my-courses', { params })
}
export function assignCourse(userId, courseId) {
  return request.post('/student-course/assign', null, { params: { userId, courseId } })
}
export function batchAssign(userIds, courseId) {
  return request.post('/student-course/batch-assign', userIds, { params: { courseId } })
}
export function getCourseStudents(courseId) {
  return request.get(`/student-course/course-students/${courseId}`)
}
export function getExamSessions(examId) {
  return request.get(`/exam/session/list/${examId}`)
}
export function addExamSession(data) {
  return request.post('/exam/session', data)
}
export function deleteExamSession(id) {
  return request.delete(`/exam/session/${id}`)
}
export function getConfigList() {
  return request.get('/config/list')
}
export function getConfigByGroup() {
  return request.get('/config/group')
}
export function setConfig(key, value) {
  return request.post('/config/set', null, { params: { key, value } })
}
export function updateConfig(data) {
  return request.put('/config', data)
}
export function getMajorList() {
  return request.get('/major/list')
}
export function createMajor(data) {
  return request.post('/major', data)
}
export function updateMajor(data) {
  return request.put('/major', data)
}
export function deleteMajor(id) {
  return request.delete(`/major/${id}`)
}
export function getPermissionTree() {
  return request.get('/permission/tree')
}
export function getPermissionsByRole(role) {
  return request.get(`/permission/role/${role}`)
}
export function assignPermissions(role, permissionIds) {
  return request.post('/permission/assign', permissionIds, { params: { role } })
}
