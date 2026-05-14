<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>课程管理</h3>
        <el-input v-model="keyword" placeholder="搜索课程" style="width:300px" @keyup.enter="loadData" clearable />
      </div>
      <el-table :data="courses" stripe>
        <el-table-column prop="title" label="课程名称" />
        <el-table-column prop="teacherName" label="讲师" width="100" />
        <el-table-column prop="studentCount" label="学习人数" width="100" />
        <el-table-column prop="isFree" label="类型" width="80">
          <template #default="{ row }"><el-tag :type="row.isFree ? 'success' : 'warning'">{{ row.isFree ? '免费' : '付费' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }"><el-tag :type="['info','','success','warning'][row.status]">{{ ['草稿','已发布','下架'][row.status] }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button v-if="row.status === 1" size="small" type="warning" @click="handleStatus(row.id, 2)">下架</el-button>
            <el-button v-if="row.status === 0 || row.status === 2" size="small" type="success" @click="handleStatus(row.id, 1)">发布</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="pageNum" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top:16px;justify-content:center" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getCourseList, deleteCourse, updateCourseStatus } from '../../api/course'
import { ElMessage, ElMessageBox } from 'element-plus'

const courses = ref([])
const keyword = ref('')
const pageNum = ref(1)
const total = ref(0)

async function loadData() {
  const res = await getCourseList({ pageNum: pageNum.value, pageSize: 10, keyword: keyword.value })
  courses.value = res.data.rows || []
  total.value = res.data.total || 0
}

async function handleStatus(id, status) { await updateCourseStatus(id, status); ElMessage.success('操作成功'); loadData() }
async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteCourse(id); ElMessage.success('删除成功'); loadData()
}

onMounted(loadData)
</script>
