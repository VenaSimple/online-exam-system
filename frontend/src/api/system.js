import request from '../utils/request'

export function getStudyRecords(params) {
  return request.get('/study/records', { params })
}

export function getStudyDetail(courseId) {
  return request.get('/study/detail', { params: { courseId } })
}

export function updateProgress(data) {
  return request.post('/study/progress', null, { params: data })
}

export function getKnowledgeList(params) {
  return request.get('/knowledge/list', { params })
}

export function getKnowledgeById(id) {
  return request.get(`/knowledge/${id}`)
}

export function createKnowledge(data) {
  return request.post('/knowledge', data)
}

export function updateKnowledge(data) {
  return request.put('/knowledge', data)
}

export function deleteKnowledge(id) {
  return request.delete(`/knowledge/${id}`)
}

export function getNoticeList(params) {
  return request.get('/notice/list', { params })
}

export function getNoticeById(id) {
  return request.get(`/notice/${id}`)
}

export function createNotice(data) {
  return request.post('/notice', data)
}

export function updateNotice(data) {
  return request.put('/notice', data)
}

export function deleteNotice(id) {
  return request.delete(`/notice/${id}`)
}

export function getNewsList(params) {
  return request.get('/news/list', { params })
}

export function getDashboardStats() {
  return request.get('/dashboard/stats')
}

export function getOrgTree() {
  return request.get('/org/tree')
}

export function createOrg(data) {
  return request.post('/org', data)
}

export function deleteOrg(id) {
  return request.delete(`/org/${id}`)
}
