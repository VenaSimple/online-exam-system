<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in stats" :key="item.label">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" :style="{ background: item.color }">
            <el-icon :size="32"><component :is="item.icon" /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ item.value }}</div>
            <div class="stat-label">{{ item.label }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top:20px">
      <el-col :span="12">
        <el-card><template #header>用户注册趋势</template>
          <div style="height:300px;display:flex;align-items:center;justify-content:center;color:#999">
            <el-empty description="需要ECharts展示" />
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card><template #header>考试统计</template>
          <div style="height:300px;display:flex;align-items:center;justify-content:center;color:#999">
            <el-empty description="需要ECharts展示" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getDashboardStats } from '../../api/system'

const stats = ref([
  { label: '用户总数', value: 0, icon: 'User', color: '#409EFF' },
  { label: '课程总数', value: 0, icon: 'VideoPlay', color: '#67C23A' },
  { label: '考试总数', value: 0, icon: 'EditPen', color: '#E6A23C' },
  { label: '题目总数', value: 0, icon: 'Document', color: '#F56C6C' }
])

onMounted(async () => {
  try {
    const res = await getDashboardStats()
    stats.value[0].value = res.data.userCount || 0
    stats.value[1].value = res.data.courseCount || 0
    stats.value[2].value = res.data.examCount || 0
    stats.value[3].value = res.data.questionCount || 0
  } catch (e) {}
})
</script>

<style scoped>
.stat-card { display: flex; align-items: center; padding: 20px; }
.stat-icon { width: 64px; height: 64px; border-radius: 12px; display: flex; align-items: center; justify-content: center; color: #fff; margin-right: 16px; }
.stat-value { font-size: 28px; font-weight: bold; color: #333; }
.stat-label { font-size: 14px; color: #999; margin-top: 4px; }
</style>
