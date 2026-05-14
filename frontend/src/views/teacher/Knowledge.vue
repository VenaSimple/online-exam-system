<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>知识库管理</h3>
        <el-button type="primary" @click="handleAdd">新增文章</el-button>
      </div>
      <el-table :data="list" stripe>
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="categoryName" label="分类" width="120" />
        <el-table-column prop="authorName" label="作者" width="100" />
        <el-table-column prop="viewCount" label="浏览量" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }"><el-tag :type="row.status ? 'success' : 'info'">{{ row.status ? '已发布' : '草稿' }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getKnowledgeList, createKnowledge, updateKnowledge, deleteKnowledge } from '../../api/system'
import { ElMessage, ElMessageBox } from 'element-plus'

const list = ref([])

async function loadData() {
  const res = await getKnowledgeList({ pageNum: 1, pageSize: 50 })
  list.value = res.data.rows || []
}

async function handleAdd() {
  await ElMessageBox.prompt('请输入标题', '新增知识文章')
  await createKnowledge({ title: '新文章', content: '', status: 0 })
  ElMessage.success('创建成功')
  loadData()
}

function handleEdit(row) { /* navigate to edit */ }

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteKnowledge(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
