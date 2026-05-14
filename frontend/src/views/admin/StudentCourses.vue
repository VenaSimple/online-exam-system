<template>
  <el-card>
    <h3 style="margin-bottom:16px">学员开课</h3>
    <el-form :inline="true" style="margin-bottom:16px">
      <el-form-item label="学员ID"><el-input v-model="userId" placeholder="输入学员用户ID" /></el-form-item>
      <el-form-item label="课程"><el-select v-model="courseId" placeholder="选择课程">
        <el-option v-for="c in courses" :key="c.id" :label="c.title" :value="c.id" />
      </el-select></el-form-item>
      <el-form-item><el-button type="primary" @click="handleAssign">开课</el-button></el-form-item>
    </el-form>
    <el-table :data="list" stripe>
      <el-table-column prop="userId" label="学员ID" width="80" />
      <el-table-column prop="courseTitle" label="课程" />
      <el-table-column prop="progress" label="进度" width="120">
        <template #default="{ row }"><el-progress :percentage="row.progress" /></template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status ? 'success' : 'info'">{{ row.status ? '正常' : '禁用' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="createTime" label="开课时间" width="180" />
    </el-table>
  </el-card>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { assignCourse, getCourseStudents } from '../../api/portal'
import { getCourseList } from '../../api/course'
import { ElMessage } from 'element-plus'
const userId = ref('')
const courseId = ref(null)
const courses = ref([])
const list = ref([])
async function loadCourses() { const r = await getCourseList({ pageNum: 1, pageSize: 100 }); courses.value = r.data?.rows || [] }
async function handleAssign() {
  if (!userId.value || !courseId.value) return ElMessage.warning('请填写学员ID和选择课程')
  await assignCourse(userId.value, courseId.value)
  ElMessage.success('开课成功')
  if (courseId.value) { const r = await getCourseStudents(courseId.value); list.value = r.data || [] }
}
onMounted(loadCourses)
</script>
