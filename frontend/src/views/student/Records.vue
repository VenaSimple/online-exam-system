<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:16px">学习记录</h3>
      <el-table :data="records" stripe>
        <el-table-column prop="courseTitle" label="课程名称" />
        <el-table-column prop="chapterTitle" label="章节" />
        <el-table-column prop="progress" label="进度">
          <template #default="{ row }"><el-progress :percentage="row.progress" /></template>
        </el-table-column>
        <el-table-column prop="duration" label="学习时长">
          <template #default="{ row }">{{ Math.floor(row.duration / 60) }}分钟</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最近学习" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStudyRecords } from '../../api/system'

const records = ref([])

onMounted(async () => {
  const res = await getStudyRecords({ pageNum: 1, pageSize: 20 })
  records.value = res.data.rows || []
})
</script>
