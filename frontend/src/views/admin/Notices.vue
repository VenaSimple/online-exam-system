<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>通知公告</h3>
        <el-button type="primary" @click="handleAdd">新增公告</el-button>
      </div>
      <el-table :data="list" stripe>
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }"><el-tag>{{ row.type === 1 ? '通知' : '公告' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="isTop" label="置顶" width="80">
          <template #default="{ row }"><el-tag :type="row.isTop ? 'danger' : 'info'">{{ row.isTop ? '是' : '否' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="publisherName" label="发布人" width="100" />
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="editForm.id ? '编辑' : '新增'" width="600px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="标题"><el-input v-model="editForm.title" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="editForm.type"><el-option label="通知" :value="1" /><el-option label="公告" :value="2" /></el-select></el-form-item>
        <el-form-item label="置顶"><el-switch v-model="editForm.isTop" :active-value="1" :inactive-value="0" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="editForm.content" type="textarea" :rows="6" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getNoticeList, createNotice, updateNotice, deleteNotice } from '../../api/system'
import { ElMessage, ElMessageBox } from 'element-plus'

const list = ref([])
const dialogVisible = ref(false)
const editForm = reactive({ id: null, title: '', content: '', type: 1, isTop: 0 })

async function loadData() {
  const res = await getNoticeList({ pageNum: 1, pageSize: 50 })
  list.value = res.data.rows || []
}

function handleAdd() { Object.assign(editForm, { id: null, title: '', content: '', type: 1, isTop: 0 }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(editForm, row); dialogVisible.value = true }

async function handleSave() {
  if (editForm.id) await updateNotice(editForm); else await createNotice(editForm)
  ElMessage.success('保存成功'); dialogVisible.value = false; loadData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteNotice(id); ElMessage.success('删除成功'); loadData()
}

onMounted(loadData)
</script>
