<template>
  <el-container class="teacher-layout">
    <el-aside width="220px" class="sidebar">
      <div class="logo">
        <el-icon :size="28"><School /></el-icon>
        <span>教学管理</span>
      </div>
      <el-menu :default-active="currentRoute" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/teacher/home"><el-icon><HomeFilled /></el-icon>首页</el-menu-item>
        <el-menu-item index="/teacher/courses"><el-icon><VideoPlay /></el-icon>课程管理</el-menu-item>
        <el-menu-item index="/teacher/questions"><el-icon><Document /></el-icon>题库管理</el-menu-item>
        <el-menu-item index="/teacher/papers"><el-icon><Notebook /></el-icon>试卷管理</el-menu-item>
        <el-menu-item index="/teacher/exams"><el-icon><EditPen /></el-icon>考试管理</el-menu-item>
        <el-menu-item index="/teacher/records"><el-icon><DataLine /></el-icon>成绩管理</el-menu-item>
        <el-menu-item index="/teacher/knowledge"><el-icon><Collection /></el-icon>知识库</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <span class="welcome">欢迎，{{ userStore.realName || '老师' }}</span>
        <el-dropdown>
          <span class="user-info"><el-avatar :size="32">{{ (userStore.realName || 'T')[0] }}</el-avatar></span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-main class="main-content"><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '../stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const currentRoute = computed(() => route.path)

function handleLogout() {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.teacher-layout { height: 100vh; }
.sidebar { background: #304156; overflow-y: auto; }
.logo { height: 60px; display: flex; align-items: center; justify-content: center; color: #fff; font-size: 18px; font-weight: bold; gap: 8px; }
.header { background: #fff; border-bottom: 1px solid #eee; display: flex; align-items: center; justify-content: space-between; padding: 0 20px; }
.welcome { font-size: 14px; color: #666; }
.user-info { cursor: pointer; }
.main-content { background: #f5f7fa; padding: 20px; overflow-y: auto; }
</style>
