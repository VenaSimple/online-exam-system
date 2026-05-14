<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:16px">
        <h3>课程中心</h3>
        <el-input v-model="keyword" placeholder="搜索课程" style="width:300px" @keyup.enter="loadData" clearable>
          <template #append><el-button @click="loadData"><el-icon><Search /></el-icon></el-button></template>
        </el-input>
      </div>
      <el-row :gutter="16">
        <el-col :span="6" v-for="course in courses" :key="course.id">
          <el-card shadow="hover" class="course-card" @click="router.push(`/student/course/${course.id}`)">
            <div class="course-cover" style="background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:#fff;font-size:28px;height:140px">
              {{ course.title?.substring(0,4) }}
            </div>
            <div class="course-info">
              <h4>{{ course.title }}</h4>
              <p style="color:#999">{{ course.teacherName }}</p>
              <div style="display:flex;justify-content:space-between;align-items:center;margin-top:8px">
                <el-tag :type="course.isFree ? 'success' : 'warning'" size="small">{{ course.isFree ? '免费' : '¥' + course.price }}</el-tag>
                <span style="color:#999;font-size:12px">{{ course.studentCount }}人学习</span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top:20px;justify-content:center" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getStudentCourses } from '../../api/course'

const router = useRouter()
const courses = ref([])
const keyword = ref('')
const pageNum = ref(1)
const pageSize = ref(12)
const total = ref(0)

async function loadData() {
  const res = await getStudentCourses({ pageNum: pageNum.value, pageSize: pageSize.value, keyword: keyword.value })
  courses.value = res.data.rows || []
  total.value = res.data.total || 0
}

onMounted(loadData)
</script>

<style scoped>
.course-card { cursor: pointer; margin-bottom: 16px; }
.course-card:hover { transform: translateY(-4px); transition: all 0.3s; }
.course-info h4 { margin: 8px 0 4px; font-size: 14px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
</style>
