<template>
  <el-card>
    <div style="display:flex;justify-content:space-between;margin-bottom:16px">
      <h3>轮播图管理</h3>
      <el-button type="primary" @click="handleAdd">新增轮播图</el-button>
    </div>
    <el-table :data="list" stripe>
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="imageUrl" label="图片地址" show-overflow-tooltip />
      <el-table-column prop="linkUrl" label="跳转链接" show-overflow-tooltip />
      <el-table-column prop="sort" label="排序" width="80" />
      <el-table-column prop="status" label="状态" width="100">
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
        <el-form-item label="标题"><el-input v-model="editForm.title" /></el-form-item>
        <el-form-item label="图片地址"><el-input v-model="editForm.imageUrl" /></el-form-item>
        <el-form-item label="跳转链接"><el-input v-model="editForm.linkUrl" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="editForm.sort" :min="0" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="editForm.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSave">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getBannerList, createBanner, updateBanner, deleteBanner } from '../../api/portal'
import { ElMessage, ElMessageBox } from 'element-plus'
const list = ref([])
const dialogVisible = ref(false)
const editForm = reactive({ id: null, title: '', imageUrl: '', linkUrl: '', sort: 0, status: 1 })
async function loadData() { const r = await getBannerList(); list.value = r.data || [] }
function handleAdd() { Object.assign(editForm, { id: null, title: '', imageUrl: '', linkUrl: '', sort: 0, status: 1 }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(editForm, row); dialogVisible.value = true }
async function handleSave() { if (editForm.id) await updateBanner(editForm); else await createBanner(editForm); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() }
async function handleDelete(id) { await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' }); await deleteBanner(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>
