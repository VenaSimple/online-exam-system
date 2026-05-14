<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>考试管理</h3>
        <el-button type="primary" @click="router.push('/teacher/exam/edit')">新增考试</el-button>
      </div>
      <el-table :data="exams" stripe>
        <el-table-column prop="title" label="考试名称" />
        <el-table-column prop="paperTitle" label="试卷" width="200" />
        <el-table-column prop="examType" label="类型" width="100">
          <template #default="{ row }">{{ ['', '正式', '模拟', '练习'][row.examType] }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }"><el-tag :type="['info','success','warning','danger'][row.status]">{{ ['草稿','已发布','进行中','已结束'][row.status] }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column label="操作" width="280">
          <template #default="{ row }">
            <el-button size="small" @click="router.push(`/teacher/exam/edit/${row.id}`)">编辑</el-button>
            <el-button v-if="row.status === 0" size="small" type="success" @click="handlePublish(row.id)">发布</el-button>
            <el-button v-if="row.status === 2" size="small" type="warning" @click="handleEnd(row.id)">结束</el-button>
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
import { getExamList, deleteExam, publishExam, endExam } from '../../api/exam'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const exams = ref([])

async function loadData() {
  const res = await getExamList({ pageNum: 1, pageSize: 50 })
  exams.value = res.data.rows || []
}

async function handlePublish(id) { await publishExam(id); ElMessage.success('已发布'); loadData() }
async function handleEnd(id) { await endExam(id); ElMessage.success('已结束'); loadData() }
async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteExam(id); ElMessage.success('删除成功'); loadData()
}

onMounted(loadData)
</script>
