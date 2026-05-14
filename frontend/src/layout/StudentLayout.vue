<template>
  <el-container class="student-layout">
    <el-aside width="220px" class="sidebar">
      <div class="logo">
        <el-icon :size="28"><Reading /></el-icon>
        <span>在线学习</span>
      </div>
      <el-menu :default-active="currentRoute" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/student/home"><el-icon><HomeFilled /></el-icon>首页</el-menu-item>
        <el-menu-item index="/student/courses"><el-icon><VideoPlay /></el-icon>课程学习</el-menu-item>
        <el-menu-item index="/student/exams"><el-icon><EditPen /></el-icon>我的考试</el-menu-item>
        <el-menu-item index="/student/practice"><el-icon><Document /></el-icon>试题练习</el-menu-item>
        <el-menu-item index="/student/knowledge"><el-icon><Collection /></el-icon>知识库</el-menu-item>
        <el-menu-item index="/student/records"><el-icon><DataLine /></el-icon>学习记录</el-menu-item>
        <el-menu-item index="/student/profile"><el-icon><User /></el-icon>个人中心</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <span class="welcome">欢迎，{{ userStore.realName || '同学' }}</span>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="32">{{ (userStore.realName || 'U')[0] }}</el-avatar>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="router.push('/student/profile')">个人中心</el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main-content">
        <router-view />
      </el-main>
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
.student-layout { height: 100vh; }
.sidebar { background: #304156; overflow-y: auto; }
.logo { height: 60px; display: flex; align-items: center; justify-content: center; color: #fff; font-size: 18px; font-weight: bold; gap: 8px; }
.header { background: #fff; border-bottom: 1px solid #eee; display: flex; align-items: center; justify-content: space-between; padding: 0 20px; }
.welcome { font-size: 14px; color: #666; }
.user-info { cursor: pointer; display: flex; align-items: center; }
.main-content { background: #f5f7fa; padding: 20px; overflow-y: auto; }
</style>
