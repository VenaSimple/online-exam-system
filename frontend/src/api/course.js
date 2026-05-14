import request from '../utils/request'

// 课程 CRUD
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

// 课程分类
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

// 课程章节
export function getChapters(courseId) {
  return request.get(`/course/chapter/list/${courseId}`)
}

export function getChapterById(id) {
  return request.get(`/course/chapter/${id}`)
}

export function createChapter(data) {
  return request.post('/course/chapter', data)
}

export function updateChapter(data) {
  return request.put('/course/chapter', data)
}

export function deleteChapter(id) {
  return request.delete(`/course/chapter/${id}`)
}

// 课程留言
export function getMessages(courseId) {
  return request.get(`/course/message/list/${courseId}`)
}

export function createMessage(data) {
  return request.post('/course/message', data)
}

export function deleteMessage(id) {
  return request.delete(`/course/message/${id}`)
}

export function updateMessageStatus(id, status) {
  return request.put(`/course/message/${id}/status`, null, { params: { status } })
}
