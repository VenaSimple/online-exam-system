<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>题库管理</h3>
        <el-button type="primary" @click="router.push('/teacher/question/edit')">新增题目</el-button>
      </div>
      <el-form :inline="true" style="margin-bottom:16px">
        <el-form-item><el-input v-model="keyword" placeholder="搜索题目" clearable @keyup.enter="loadData" /></el-form-item>
        <el-form-item>
          <el-select v-model="categoryId" placeholder="题目分类" clearable>
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="type" placeholder="题目类型" clearable>
            <el-option label="单选" :value="1" /><el-option label="多选" :value="2" /><el-option label="判断" :value="3" />
            <el-option label="填空" :value="4" /><el-option label="简答" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="difficulty" placeholder="难度" clearable>
            <el-option label="简单" :value="1" /><el-option label="中等" :value="2" /><el-option label="困难" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
      </el-form>
      <el-table :data="questions" stripe>
        <el-table-column prop="content" label="题目内容" show-overflow-tooltip />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">{{ ['', '单选', '多选', '判断', '填空', '简答'][row.type] }}</template>
        </el-table-column>
        <el-table-column prop="categoryName" label="分类" width="120" />
        <el-table-column prop="difficulty" label="难度" width="80">
          <template #default="{ row }"><el-tag :type="['','success','warning','danger'][row.difficulty]">{{ ['', '简单', '中等', '困难'][row.difficulty] }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="score" label="分值" width="60" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="router.push(`/teacher/question/edit/${row.id}`)">编辑</el-button>
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
import { useRouter } from 'vue-router'
import { getQuestionList, deleteQuestion, getAllQuestionCategories } from '../../api/question'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const questions = ref([])
const categories = ref([])
const keyword = ref('')
const type = ref(null)
const categoryId = ref(null)
const difficulty = ref(null)
const pageNum = ref(1)
const total = ref(0)

async function loadData() {
  const res = await getQuestionList({
    pageNum: pageNum.value, pageSize: 10,
    keyword: keyword.value, type: type.value,
    categoryId: categoryId.value, difficulty: difficulty.value
  })
  questions.value = res.data.rows || []
  total.value = res.data.total || 0
}

async function loadCategories() {
  const res = await getAllQuestionCategories()
  categories.value = res.data || []
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteQuestion(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => { loadData(); loadCategories() })
</script>
