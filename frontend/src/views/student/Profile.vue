<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card style="text-align:center">
          <el-avatar :size="80">{{ (userStore.realName || 'U')[0] }}</el-avatar>
          <h3 style="margin:12px 0">{{ userStore.realName }}</h3>
          <p style="color:#999">{{ userStore.username }}</p>
          <el-tag>{{ userStore.role === 'student' ? '学生' : userStore.role === 'teacher' ? '教师' : '管理员' }}</el-tag>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <h3 style="margin-bottom:16px">修改密码</h3>
          <el-form ref="pwdForm" :model="pwdForm" :rules="pwdRules" style="max-width:400px">
            <el-form-item prop="oldPassword">
              <el-input v-model="pwdForm.oldPassword" type="password" placeholder="旧密码" show-password />
            </el-form-item>
            <el-form-item prop="newPassword">
              <el-input v-model="pwdForm.newPassword" type="password" placeholder="新密码" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleChangePwd">确认修改</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '../../stores/user'
import { changePassword } from '../../api/user'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const pwdForm = reactive({ oldPassword: '', newPassword: '' })
const pwdRules = {
  oldPassword: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }, { min: 6, message: '至少6位', trigger: 'blur' }]
}

async function handleChangePwd() {
  await changePassword(pwdForm)
  ElMessage.success('密码修改成功')
  pwdForm.oldPassword = ''
  pwdForm.newPassword = ''
}
</script>
