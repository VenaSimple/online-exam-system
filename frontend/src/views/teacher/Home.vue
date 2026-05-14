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
        <el-card><template #header>近期考试</template><el-empty description="暂无数据" /></el-card>
      </el-col>
      <el-col :span="12">
        <el-card><template #header>课程动态</template><el-empty description="暂无数据" /></el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref } from 'vue'
const stats = ref([
  { label: '我的课程', value: 0, icon: 'VideoPlay', color: '#409EFF' },
  { label: '题库数量', value: 0, icon: 'Document', color: '#67C23A' },
  { label: '待批改', value: 0, icon: 'EditPen', color: '#E6A23C' },
  { label: '学生数', value: 0, icon: 'User', color: '#F56C6C' }
])
</script>

<style scoped>
.stat-card { display: flex; align-items: center; padding: 20px; }
.stat-icon { width: 64px; height: 64px; border-radius: 12px; display: flex; align-items: center; justify-content: center; color: #fff; margin-right: 16px; }
.stat-value { font-size: 28px; font-weight: bold; color: #333; }
.stat-label { font-size: 14px; color: #999; margin-top: 4px; }
</style>
