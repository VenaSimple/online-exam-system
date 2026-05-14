<template>
  <div class="student-home">
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
      <el-col :span="16">
        <el-card>
          <template #header><span>热门课程</span></template>
          <el-row :gutter="16">
            <el-col :span="8" v-for="course in hotCourses" :key="course.id">
              <el-card shadow="hover" class="course-card" @click="router.push(`/student/course/${course.id}`)">
                <div class="course-cover" style="background:#409EFF;display:flex;align-items:center;justify-content:center;color:#fff;font-size:24px;height:120px">
                  {{ course.title?.substring(0,4) }}
                </div>
                <div class="course-info">
                  <h4>{{ course.title }}</h4>
                  <p>{{ course.teacherName }}</p>
                  <el-rate v-model="course.avgScore" disabled :max="5" size="small" />
                </div>
              </el-card>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header><span>通知公告</span></template>
          <div v-for="notice in notices" :key="notice.id" class="notice-item">
            <el-tag size="small" :type="notice.isTop ? 'danger' : 'info'">{{ notice.isTop ? '置顶' : '通知' }}</el-tag>
            <span class="notice-title">{{ notice.title }}</span>
          </div>
          <el-empty v-if="!notices.length" description="暂无公告" :image-size="60" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getStudentCourses } from '../../api/course'
import { getNoticeList } from '../../api/system'

const router = useRouter()
const hotCourses = ref([])
const notices = ref([])
const stats = ref([
  { label: '在学课程', value: 0, icon: 'VideoPlay', color: '#409EFF' },
  { label: '待考科目', value: 0, icon: 'EditPen', color: '#E6A23C' },
  { label: '已完成', value: 0, icon: 'CircleCheck', color: '#67C23A' },
  { label: '学习时长', value: '0h', icon: 'Timer', color: '#F56C6C' }
])

onMounted(async () => {
  try {
    const res = await getStudentCourses({ pageNum: 1, pageSize: 6 })
    hotCourses.value = res.data.rows || []
    stats.value[0].value = res.data.total || 0
  } catch (e) {}
  try {
    const res = await getNoticeList({ pageNum: 1, pageSize: 5 })
    notices.value = res.data.rows || []
  } catch (e) {}
})
</script>

<style scoped>
.stat-card { display: flex; align-items: center; padding: 20px; }
.stat-icon { width: 64px; height: 64px; border-radius: 12px; display: flex; align-items: center; justify-content: center; color: #fff; margin-right: 16px; }
.stat-value { font-size: 28px; font-weight: bold; color: #333; }
.stat-label { font-size: 14px; color: #999; margin-top: 4px; }
.course-card { cursor: pointer; margin-bottom: 16px; }
.course-card:hover { transform: translateY(-4px); transition: all 0.3s; }
.course-info h4 { margin: 8px 0 4px; font-size: 14px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.course-info p { color: #999; font-size: 12px; }
.notice-item { padding: 8px 0; border-bottom: 1px solid #f0f0f0; display: flex; align-items: center; gap: 8px; }
.notice-title { font-size: 14px; cursor: pointer; }
.notice-title:hover { color: #409EFF; }
</style>
