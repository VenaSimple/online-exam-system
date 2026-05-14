<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>试卷管理</h3>
        <el-button type="primary" @click="router.push('/teacher/paper/edit')">新增试卷</el-button>
      </div>
      <el-table :data="papers" stripe>
        <el-table-column prop="title" label="试卷名称" />
        <el-table-column prop="questionCount" label="题目数量" width="100" />
        <el-table-column prop="totalScore" label="总分" width="80" />
        <el-table-column prop="passScore" label="及格分" width="80" />
        <el-table-column prop="duration" label="时长(分钟)" width="120" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">{{ row.type === 1 ? '固定试卷' : '随机试卷' }}</template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="router.push(`/teacher/paper/edit/${row.id}`)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getPaperList, deletePaper } from '../../api/exam'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const papers = ref([])

async function loadData() {
  const res = await getPaperList({ pageNum: 1, pageSize: 50 })
  papers.value = res.data.rows || []
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deletePaper(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
