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

export function getQuestionCategories() {
  return request.get('/question/category/list')
}

export function createQuestionCategory(data) {
  return request.post('/question/category', data)
}

export function deleteQuestionCategory(id) {
  return request.delete(`/question/category/${id}`)
}
