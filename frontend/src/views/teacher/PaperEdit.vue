<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:20px">{{ isEdit ? '编辑试卷' : '新增试卷' }}</h3>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" style="max-width:700px">
        <el-form-item label="试卷名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入试卷名称" />
        </el-form-item>
        <el-form-item label="考试时长"><el-input-number v-model="form.duration" :min="10" /> 分钟</el-form-item>
        <el-form-item label="总分"><el-input-number v-model="form.totalScore" :min="10" /></el-form-item>
        <el-form-item label="及格分"><el-input-number v-model="form.passScore" :min="0" /></el-form-item>
        <el-form-item label="试卷类型">
          <el-select v-model="form.type"><el-option label="固定试卷" :value="1" /><el-option label="随机试卷" :value="2" /></el-select>
        </el-form-item>
        <el-form-item label="说明"><el-input v-model="form.description" type="textarea" :rows="2" /></el-form-item>

        <el-divider>选择题目</el-divider>
        <el-button type="primary" size="small" @click="showQuestionDialog = true">从题库选题</el-button>
        <el-table :data="selectedQuestions" style="margin-top:12px" border size="small">
          <el-table-column prop="content" label="题目" show-overflow-tooltip />
          <el-table-column prop="type" label="类型" width="80">
            <template #default="{ row }">{{ ['', '单选', '多选', '判断', '填空', '简答'][row.type] }}</template>
          </el-table-column>
          <el-table-column label="分值" width="120">
            <template #default="{ row }"><el-input-number v-model="row.score" :min="1" size="small" /></template>
          </el-table-column>
          <el-table-column label="排序" width="100">
            <template #default="{ row }"><el-input-number v-model="row.sort" :min="1" size="small" /></template>
          </el-table-column>
          <el-table-column label="操作" width="80">
            <template #default="{ $index }"><el-button type="danger" size="small" @click="selectedQuestions.splice($index, 1)">移除</el-button></template>
          </el-table-column>
        </el-table>

        <el-form-item style="margin-top:20px">
          <el-button type="primary" @click="handleSubmit">保存试卷</el-button>
          <el-button @click="router.back()">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog v-model="showQuestionDialog" title="从题库选择" width="700px">
      <el-table :data="allQuestions" @selection-change="handleSelection" size="small">
        <el-table-column type="selection" width="50" />
        <el-table-column prop="content" label="题目" show-overflow-tooltip />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">{{ ['', '单选', '多选', '判断', '填空', '简答'][row.type] }}</template>
        </el-table-column>
      </el-table>
      <template #footer>
        <el-button @click="showQuestionDialog = false">关闭</el-button>
        <el-button type="primary" @click="confirmSelection">确认选择</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getPaperById, createPaper, updatePaper } from '../../api/exam'
import { getQuestionList } from '../../api/question'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => !!route.params.id)
const showQuestionDialog = ref(false)
const allQuestions = ref([])
const tempSelection = ref([])
const selectedQuestions = ref([])

const form = reactive({ id: null, title: '', description: '', totalScore: 100, passScore: 60, duration: 60, type: 1, status: 0 })
const rules = { title: [{ required: true, message: '请输入试卷名称', trigger: 'blur' }] }

function handleSelection(rows) { tempSelection.value = rows }
function confirmSelection() {
  for (const q of tempSelection.value) {
    if (!selectedQuestions.value.find(sq => sq.questionId === q.id)) {
      selectedQuestions.value.push({ questionId: q.id, content: q.content, type: q.type, score: q.score || 2, sort: selectedQuestions.value.length + 1 })
    }
  }
  showQuestionDialog.value = false
}

onMounted(async () => {
  const qRes = await getQuestionList({ pageNum: 1, pageSize: 100 })
  allQuestions.value = qRes.data.rows || []
  if (isEdit.value) {
    const res = await getPaperById(route.params.id)
    Object.assign(form, res.data)
    if (res.data.questions) {
      selectedQuestions.value = res.data.questions.map((q, idx) => ({
        questionId: q.questionId, content: '', type: 0, score: q.score, sort: q.sort || idx + 1
      }))
    }
  }
})

async function handleSubmit() {
  form.questions = selectedQuestions.value.map(q => ({ questionId: q.questionId, score: q.score, sort: q.sort }))
  if (isEdit.value) await updatePaper(form); else await createPaper(form)
  ElMessage.success('保存成功')
  router.push('/teacher/papers')
}
</script>
