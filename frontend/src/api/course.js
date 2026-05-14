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

// 课程分类 - 路径与后端 /course/category 对齐
export function getCourseCategories(params) {
  return request.get('/course/category/list', { params })
}

export function getAllCourseCategories() {
  return request.get('/course/category/all')
}

export function getCourseCategoryById(id) {
  return request.get(`/course/category/${id}`)
}

export function createCategory(data) {
  return request.post('/course/category', data)
}

export function updateCategory(data) {
  return request.put('/course/category', data)
}

export function deleteCategory(id) {
  return request.delete(`/course/category/${id}`)
}
