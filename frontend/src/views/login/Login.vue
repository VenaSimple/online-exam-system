<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h1>在线培训考试系统</h1>
        <p>学、练、考一体化在线学习平台</p>
      </div>
      <el-tabs v-model="activeRole" class="role-tabs">
        <el-tab-pane label="学生登录" name="student" />
        <el-tab-pane label="教师登录" name="teacher" />
        <el-tab-pane label="管理员登录" name="admin" />
      </el-tabs>
      <el-form ref="formRef" :model="form" :rules="rules" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" :prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" :prefix-icon="Lock" size="large" show-password @keyup.enter="handleLogin" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" :loading="loading" style="width:100%" @click="handleLogin">登 录</el-button>
        </el-form-item>
      </el-form>
      <div class="login-footer">
        还没有账号？<router-link to="/register">立即注册</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user'
import { login } from '../../api/auth'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)
const activeRole = ref('student')

const form = reactive({ username: '', password: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  loading.value = true
  try {
    const res = await login({ ...form, role: activeRole.value })
    userStore.setLoginInfo(res.data)
    ElMessage.success('登录成功')
    router.push(userStore.getHomePath())
  } catch (e) { /* handled by interceptor */ }
  finally { loading.value = false }
}
</script>

<style scoped>
.login-container { height: 100vh; display: flex; align-items: center; justify-content: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.login-card { width: 420px; padding: 40px; background: #fff; border-radius: 12px; box-shadow: 0 20px 60px rgba(0,0,0,0.3); }
.login-header { text-align: center; margin-bottom: 20px; }
.login-header h1 { font-size: 24px; color: #333; margin-bottom: 8px; }
.login-header p { color: #999; font-size: 14px; }
.login-form { margin-top: 20px; }
.login-footer { text-align: center; margin-top: 16px; font-size: 14px; color: #999; }
.login-footer a { color: #409EFF; text-decoration: none; }
</style>
