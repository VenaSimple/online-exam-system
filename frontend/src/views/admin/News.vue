<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>新闻资讯</h3>
        <el-button type="primary" @click="handleAdd">新增新闻</el-button>
      </div>
      <el-table :data="list" stripe>
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="author" label="作者" width="100" />
        <el-table-column prop="viewCount" label="浏览量" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }"><el-tag :type="row.status ? 'success' : 'info'">{{ row.status ? '已发布' : '草稿' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
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
        <el-form-item label="作者"><el-input v-model="editForm.author" /></el-form-item>
        <el-form-item label="摘要"><el-input v-model="editForm.summary" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="editForm.content" type="textarea" :rows="6" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="editForm.status" :active-value="1" :inactive-value="0" active-text="发布" inactive-text="草稿" /></el-form-item>
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
import { getNewsList, createNews, updateNews, deleteNews } from '../../api/system'
import { ElMessage, ElMessageBox } from 'element-plus'

const list = ref([])
const dialogVisible = ref(false)
const editForm = reactive({ id: null, title: '', content: '', summary: '', author: '', status: 1 })

async function loadData() {
  const res = await getNewsList({ pageNum: 1, pageSize: 50 })
  list.value = res.data.rows || []
}

function handleAdd() { Object.assign(editForm, { id: null, title: '', content: '', summary: '', author: '', status: 1 }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(editForm, row); dialogVisible.value = true }

async function handleSave() {
  if (editForm.id) await updateNews(editForm); else await createNews(editForm)
  ElMessage.success('保存成功'); dialogVisible.value = false; loadData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteNews(id); ElMessage.success('删除成功'); loadData()
}

onMounted(loadData)
</script>
