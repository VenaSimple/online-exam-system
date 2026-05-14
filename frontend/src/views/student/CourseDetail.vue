<template>
  <div v-if="course">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <h2>{{ course.title }}</h2>
          <p style="color:#999;margin:12px 0">{{ course.description }}</p>
          <div style="margin:16px 0">
            <el-tag>讲师: {{ course.teacherName }}</el-tag>
            <el-tag type="success" style="margin-left:8px">{{ course.isFree ? '免费' : '¥' + course.price }}</el-tag>
          </div>
          <el-divider />
          <h3>课程大纲</h3>
          <el-tree :data="chapters" :props="{ label: 'title', children: 'children' }" default-expand-all>
            <template #default="{ data }">
              <span style="display:flex;align-items:center;justify-content:space-between;width:100%">
                <span>{{ data.title }}</span>
                <el-button v-if="data.type === 2" type="primary" size="small" @click="startLearn(data)">开始学习</el-button>
              </span>
            </template>
          </el-tree>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <h4>课程信息</h4>
          <el-descriptions :column="1" style="margin-top:12px">
            <el-descriptions-item label="学习人数">{{ course.studentCount }}</el-descriptions-item>
            <el-descriptions-item label="平均评分"><el-rate v-model="course.avgScore" disabled /></el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getCourseById } from '../../api/course'

const route = useRoute()
const router = useRouter()
const course = ref(null)
const chapters = ref([])

onMounted(async () => {
  const res = await getCourseById(route.params.id)
  course.value = res.data
  // Build tree from chapters (simplified - in real app, fetch from API)
  chapters.value = [
    { id: 1, title: '第一章 课程概述', type: 1, children: [
      { id: 11, title: '1.1 课程介绍', type: 2, courseId: course.value.id },
      { id: 12, title: '1.2 学习指南', type: 2, courseId: course.value.id }
    ]},
    { id: 2, title: '第二章 核心内容', type: 1, children: [
      { id: 21, title: '2.1 知识点精讲', type: 2, courseId: course.value.id },
      { id: 22, title: '2.2 实操演练', type: 2, courseId: course.value.id }
    ]}
  ]
})

function startLearn(chapter) {
  router.push(`/student/learn/${course.value.id}/${chapter.id}`)
}
</script>
