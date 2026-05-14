<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>课程管理</h3>
        <el-button type="primary" @click="router.push('/teacher/course/edit')">新增课程</el-button>
      </div>
      <el-table :data="courses" stripe>
        <el-table-column prop="title" label="课程名称" />
        <el-table-column prop="teacherName" label="讲师" width="100" />
        <el-table-column prop="categoryName" label="分类" width="120" />
        <el-table-column prop="studentCount" label="学习人数" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }"><el-tag :type="['info','','success','warning'][row.status]">{{ ['草稿','已发布','下架'][row.status] }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="router.push(`/teacher/course/edit/${row.id}`)">编辑</el-button>
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
import { getCourseList, deleteCourse } from '../../api/course'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const courses = ref([])

async function loadData() {
  const res = await getCourseList({ pageNum: 1, pageSize: 50 })
  courses.value = res.data.rows || []
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteCourse(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
