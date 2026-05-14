<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>用户管理</h3>
        <div>
          <el-input v-model="keyword" placeholder="搜索用户" style="width:200px;margin-right:8px" @keyup.enter="loadData" clearable />
          <el-select v-model="roleFilter" placeholder="角色" clearable style="width:120px;margin-right:8px">
            <el-option label="管理员" value="admin" /><el-option label="教师" value="teacher" /><el-option label="学生" value="student" />
          </el-select>
          <el-button type="primary" @click="loadData">搜索</el-button>
          <el-button type="success" @click="showAddDialog">新增用户</el-button>
        </div>
      </div>
      <el-table :data="users" stripe>
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="realName" label="真实姓名" width="120" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="role" label="角色" width="100">
          <template #default="{ row }"><el-tag :type="row.role === 'admin' ? 'danger' : row.role === 'teacher' ? 'warning' : 'success'">{{ { admin: '管理员', teacher: '教师', student: '学生' }[row.role] }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }"><el-switch v-model="row.status" :active-value="1" :inactive-value="0" @change="v => handleStatusChange(row.id, v)" /></template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="warning" @click="handleResetPwd(row.id)">重置密码</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="pageNum" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top:16px;justify-content:center" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="editForm.id ? '编辑用户' : '新增用户'" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名"><el-input v-model="editForm.username" :disabled="!!editForm.id" /></el-form-item>
        <el-form-item label="真实姓名"><el-input v-model="editForm.realName" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="editForm.email" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="editForm.phone" /></el-form-item>
        <el-form-item label="角色">
          <el-select v-model="editForm.role"><el-option label="管理员" value="admin" /><el-option label="教师" value="teacher" /><el-option label="学生" value="student" /></el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSaveUser">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getUserList, createUser, updateUser, deleteUser, resetPassword, updateUserStatus } from '../../api/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const users = ref([])
const keyword = ref('')
const roleFilter = ref('')
const pageNum = ref(1)
const total = ref(0)
const dialogVisible = ref(false)
const editForm = reactive({ id: null, username: '', realName: '', email: '', phone: '', role: 'student' })

async function loadData() {
  const res = await getUserList({ pageNum: pageNum.value, pageSize: 10, keyword: keyword.value, role: roleFilter.value })
  users.value = res.data.rows || []
  total.value = res.data.total || 0
}

function showAddDialog() { Object.assign(editForm, { id: null, username: '', realName: '', email: '', phone: '', role: 'student' }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(editForm, row); dialogVisible.value = true }

async function handleSaveUser() {
  if (editForm.id) { await updateUser(editForm) } else { await createUser(editForm) }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteUser(id); ElMessage.success('删除成功'); loadData()
}

async function handleResetPwd(id) {
  await ElMessageBox.confirm('将重置为默认密码123456', '提示', { type: 'warning' })
  await resetPassword(id); ElMessage.success('已重置')
}

async function handleStatusChange(id, status) { await updateUserStatus(id, status) }

onMounted(loadData)
</script>
