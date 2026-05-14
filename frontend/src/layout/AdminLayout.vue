<template>
  <el-container class="admin-layout">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="sidebar">
      <div class="logo">
        <el-icon :size="28"><Monitor /></el-icon>
        <span v-show="!isCollapse">管理后台</span>
      </div>
      <el-menu :default-active="currentRoute" router :collapse="isCollapse" background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/admin/dashboard"><el-icon><DataAnalysis /></el-icon><span>仪表盘</span></el-menu-item>
        <el-menu-item index="/admin/users"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
        <el-menu-item index="/admin/courses"><el-icon><VideoPlay /></el-icon><span>课程管理</span></el-menu-item>
        <el-menu-item index="/admin/questions"><el-icon><Document /></el-icon><span>题库管理</span></el-menu-item>
        <el-menu-item index="/admin/papers"><el-icon><Notebook /></el-icon><span>试卷管理</span></el-menu-item>
        <el-menu-item index="/admin/exams"><el-icon><EditPen /></el-icon><span>考试管理</span></el-menu-item>
        <el-menu-item index="/admin/records"><el-icon><DataLine /></el-icon><span>成绩管理</span></el-menu-item>
        <el-menu-item index="/admin/notices"><el-icon><Bell /></el-icon><span>通知公告</span></el-menu-item>
        <el-menu-item index="/admin/news"><el-icon><ChatDotRound /></el-icon><span>新闻资讯</span></el-menu-item>
        <el-menu-item index="/admin/orgs"><el-icon><OfficeBuilding /></el-icon><span>组织架构</span></el-menu-item>
        <el-menu-item index="/admin/knowledge"><el-icon><Collection /></el-icon><span>知识库</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-icon class="collapse-btn" @click="isCollapse = !isCollapse" :size="20">
            <Fold v-if="!isCollapse" /><Expand v-else />
          </el-icon>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="32">{{ (userStore.realName || 'A')[0] }}</el-avatar>
              <span style="margin-left:8px">{{ userStore.realName }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main-content"><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '../stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const isCollapse = ref(false)
const currentRoute = computed(() => route.path)

function handleLogout() {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.admin-layout { height: 100vh; }
.sidebar { background: #304156; overflow-y: auto; transition: width 0.3s; }
.logo { height: 60px; display: flex; align-items: center; justify-content: center; color: #fff; font-size: 18px; font-weight: bold; gap: 8px; }
.header { background: #fff; border-bottom: 1px solid #eee; display: flex; align-items: center; justify-content: space-between; padding: 0 20px; }
.collapse-btn { cursor: pointer; }
.user-info { cursor: pointer; display: flex; align-items: center; }
.main-content { background: #f5f7fa; padding: 20px; overflow-y: auto; }
</style>
