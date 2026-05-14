import request from '../utils/request'

export function getExamList(params) {
  return request.get('/exam/list', { params })
}

export function getStudentExams(params) {
  return request.get('/exam/student', { params })
}

export function getExamById(id) {
  return request.get(`/exam/${id}`)
}

export function createExam(data) {
  return request.post('/exam', data)
}

export function updateExam(data) {
  return request.put('/exam', data)
}

export function deleteExam(id) {
  return request.delete(`/exam/${id}`)
}

export function publishExam(id) {
  return request.put(`/exam/${id}/publish`)
}

export function endExam(id) {
  return request.put(`/exam/${id}/end`)
}

export function startExam(examId) {
  return request.post(`/exam/${examId}/start`)
}

export function submitExam(data) {
  return request.post('/exam/submit', data)
}

export function getExamResult(recordId) {
  return request.get(`/exam/result/${recordId}`)
}

export function getExamRecords(params) {
  return request.get('/exam/records', { params })
}

// 试卷相关
export function getPaperList(params) {
  return request.get('/paper/list', { params })
}

export function getPaperById(id) {
  return request.get(`/paper/${id}`)
}

export function createPaper(data) {
  return request.post('/paper', data)
}

export function updatePaper(data) {
  return request.put('/paper', data)
}

export function deletePaper(id) {
  return request.delete(`/paper/${id}`)
}
