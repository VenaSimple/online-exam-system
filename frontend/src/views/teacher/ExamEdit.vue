<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:20px">{{ isEdit ? '编辑考试' : '新增考试' }}</h3>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" style="max-width:700px">
        <el-form-item label="考试名称" prop="title"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="选择试卷" prop="paperId">
          <el-select v-model="form.paperId" placeholder="请选择试卷">
            <el-option v-for="p in papers" :key="p.id" :label="p.title" :value="p.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="考试类型">
          <el-select v-model="form.examType"><el-option label="正式考试" :value="1" /><el-option label="模拟考试" :value="2" /><el-option label="练习" :value="3" /></el-select>
        </el-form-item>
        <el-form-item label="开始时间"><el-date-picker v-model="form.startTime" type="datetime" /></el-form-item>
        <el-form-item label="结束时间"><el-date-picker v-model="form.endTime" type="datetime" /></el-form-item>
        <el-form-item label="最大次数"><el-input-number v-model="form.maxAttempt" :min="1" /></el-form-item>
        <el-form-item label="显示答案">
          <el-select v-model="form.showAnswer"><el-option label="不显示" :value="0" /><el-option label="交卷后" :value="1" /><el-option label="结束后" :value="2" /></el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="handleSubmit">保存</el-button><el-button @click="router.back()">返回</el-button></el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getExamById, createExam, updateExam, getPaperList } from '../../api/exam'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => !!route.params.id)
const papers = ref([])

const form = reactive({ id: null, title: '', paperId: null, examType: 1, startTime: null, endTime: null, maxAttempt: 1, showAnswer: 0, showAnalysis: 0, participantType: 'all' })
const rules = { title: [{ required: true, message: '请输入考试名称', trigger: 'blur' }], paperId: [{ required: true, message: '请选择试卷', trigger: 'change' }] }

onMounted(async () => {
  const pRes = await getPaperList({ pageNum: 1, pageSize: 100 })
  papers.value = pRes.data.rows || []
  if (isEdit.value) {
    const res = await getExamById(route.params.id)
    Object.assign(form, res.data)
  }
})

async function handleSubmit() {
  if (isEdit.value) await updateExam(form); else await createExam(form)
  ElMessage.success('保存成功')
  router.push('/teacher/exams')
}
</script>
