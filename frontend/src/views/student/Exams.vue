<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:16px">我的考试</h3>
      <el-table :data="exams" stripe>
        <el-table-column prop="title" label="考试名称" />
        <el-table-column prop="paperTitle" label="试卷" width="200" />
        <el-table-column prop="examType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.examType === 1 ? 'danger' : row.examType === 2 ? 'warning' : 'info'">
              {{ ['','正式考试','模拟考试','练习'][row.examType] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="duration" label="时长(分钟)" width="120" />
        <el-table-column prop="totalScore" label="总分" width="80" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : row.status === 2 ? 'warning' : 'info'">
              {{ ['草稿','未开始','进行中','已结束'][row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button v-if="row.status === 1 || row.status === 2" type="primary" size="small" @click="router.push(`/student/exam/${row.id}`)">进入考试</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="pageNum" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top:16px;justify-content:center" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getStudentExams } from '../../api/exam'

const router = useRouter()
const exams = ref([])
const pageNum = ref(1)
const total = ref(0)

async function loadData() {
  const res = await getStudentExams({ pageNum: pageNum.value, pageSize: 10 })
  exams.value = res.data.rows || []
  total.value = res.data.total || 0
}

onMounted(loadData)
</script>
