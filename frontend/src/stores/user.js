import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userId = ref(localStorage.getItem('userId') || '')
  const username = ref(localStorage.getItem('username') || '')
  const realName = ref(localStorage.getItem('realName') || '')
  const role = ref(localStorage.getItem('role') || '')
  const avatar = ref(localStorage.getItem('avatar') || '')

  function setLoginInfo(data) {
    token.value = data.token
    userId.value = data.userId
    username.value = data.username
    realName.value = data.realName
    role.value = data.role
    avatar.value = data.avatar
    localStorage.setItem('token', data.token)
    localStorage.setItem('userId', data.userId)
    localStorage.setItem('username', data.username)
    localStorage.setItem('realName', data.realName)
    localStorage.setItem('role', data.role)
    localStorage.setItem('avatar', data.avatar || '')
  }

  function logout() {
    token.value = ''
    userId.value = ''
    username.value = ''
    realName.value = ''
    role.value = ''
    avatar.value = ''
    localStorage.removeItem('token')
    localStorage.removeItem('userId')
    localStorage.removeItem('username')
    localStorage.removeItem('realName')
    localStorage.removeItem('role')
    localStorage.removeItem('avatar')
  }

  function getHomePath() {
    if (role.value === 'admin') return '/admin/dashboard'
    if (role.value === 'teacher') return '/teacher/home'
    return '/student/home'
  }

  return { token, userId, username, realName, role, avatar, setLoginInfo, logout, getHomePath }
})
