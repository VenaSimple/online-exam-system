<template>
  <el-card>
    <div style="display:flex;justify-content:space-between;margin-bottom:16px">
      <h3>专业设置</h3>
      <el-button type="primary" @click="handleAdd">新增专业</el-button>
    </div>
    <el-table :data="list" stripe>
      <el-table-column prop="name" label="专业名称" />
      <el-table-column prop="code" label="编码" width="120" />
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status ? 'success' : 'info'">{{ row.status ? '启用' : '禁用' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="editForm.id ? '编辑' : '新增'" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="专业名称"><el-input v-model="editForm.name" /></el-form-item>
        <el-form-item label="编码"><el-input v-model="editForm.code" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="editForm.description" type="textarea" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getMajorList, createMajor, updateMajor, deleteMajor } from '../../api/portal'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([])
const dialogVisible = ref(false)
const editForm = reactive({ id: null, name: '', code: '', description: '' })
async function loadData() { const r = await getMajorList(); list.value = r.data || [] }
function handleAdd() { Object.assign(editForm, { id: null, name: '', code: '', description: '' }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(editForm, row); dialogVisible.value = true }
async function handleSave() { if (editForm.id) await updateMajor(editForm); else await createMajor(editForm); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() }
async function handleDelete(id) { await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' }); await deleteMajor(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>
