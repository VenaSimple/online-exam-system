import request from '../utils/request'

export function getCourseList(params) {
  return request.get('/course/list', { params })
}

export function getStudentCourses(params) {
  return request.get('/course/student', { params })
}

export function getCourseById(id) {
  return request.get(`/course/${id}`)
}

export function createCourse(data) {
  return request.post('/course', data)
}

export function updateCourse(data) {
  return request.put('/course', data)
}

export function deleteCourse(id) {
  return request.delete(`/course/${id}`)
}

export function updateCourseStatus(id, status) {
  return request.put(`/course/${id}/status`, null, { params: { status } })
}

export function getCourseCategories() {
  return request.get('/course/categories')
}

export function createCategory(data) {
  return request.post('/course/categories', data)
}

export function deleteCategory(id) {
  return request.delete(`/course/categories/${id}`)
}
