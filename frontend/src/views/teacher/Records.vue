<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:16px">成绩管理</h3>
      <el-table :data="records" stripe>
        <el-table-column prop="examTitle" label="考试名称" />
        <el-table-column prop="userName" label="学员" width="100" />
        <el-table-column prop="userScore" label="得分" width="80" />
        <el-table-column prop="totalScore" label="总分" width="80" />
        <el-table-column prop="isPass" label="是否通过" width="100">
          <template #default="{ row }"><el-tag :type="row.isPass ? 'success' : 'danger'">{{ row.isPass ? '通过' : '未通过' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="duration" label="用时" width="100">
          <template #default="{ row }">{{ Math.floor(row.duration / 60) }}分钟</template>
        </el-table-column>
        <el-table-column prop="endTime" label="交卷时间" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getExamRecords } from '../../api/exam'

const records = ref([])
onMounted(async () => {
  const res = await getExamRecords({ pageNum: 1, pageSize: 50 })
  records.value = res.data.rows || []
})
</script>
