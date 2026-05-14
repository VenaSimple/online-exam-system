import request from '../utils/request'

export function getQuestionList(params) {
  return request.get('/question/list', { params })
}

export function getQuestionById(id) {
  return request.get(`/question/${id}`)
}

export function createQuestion(data) {
  return request.post('/question', data)
}

export function updateQuestion(data) {
  return request.put('/question', data)
}

export function deleteQuestion(id) {
  return request.delete(`/question/${id}`)
}

export function batchDeleteQuestions(ids) {
  return request.delete('/question/batch', { data: ids })
}

export function importQuestions(data) {
  return request.post('/question/import', data)
}

// 题目分类
export function getQuestionCategories(params) {
  return request.get('/question/category/list', { params })
}

export function getAllQuestionCategories() {
  return request.get('/question/category/all')
}

export function getQuestionCategoryById(id) {
  return request.get(`/question/category/${id}`)
}

export function createQuestionCategory(data) {
  return request.post('/question/category', data)
}

export function updateQuestionCategory(data) {
  return request.put('/question/category', data)
}

export function deleteQuestionCategory(id) {
  return request.delete(`/question/category/${id}`)
}
