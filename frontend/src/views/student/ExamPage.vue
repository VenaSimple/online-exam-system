<template>
  <div v-if="examDetail">
    <el-card>
      <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:20px">
        <h2>{{ examDetail.examTitle }}</h2>
        <div>
          <el-tag type="warning">总分: {{ examDetail.totalScore }}分</el-tag>
          <el-tag type="success" style="margin-left:8px">及格: {{ examDetail.passScore }}分</el-tag>
          <el-tag style="margin-left:8px">时长: {{ examDetail.duration }}分钟</el-tag>
        </div>
      </div>
      
      <div class="question-nav">
        <span v-for="(q, idx) in examDetail.questions" :key="q.questionId" 
              class="q-nav-item" :class="{ answered: answers[q.questionId] }" 
              @click="scrollToQuestion(idx)">
          {{ idx + 1 }}
        </span>
      </div>

      <div v-for="(q, idx) in examDetail.questions" :key="q.questionId" :id="`q-${idx}`" class="question-item">
        <h4>{{ idx + 1 }}. {{ q.questionContent }} <el-tag size="small">({{ q.score }}分)</el-tag></h4>
        
        <!-- 单选题 -->
        <el-radio-group v-if="q.questionType === 1" v-model="answers[q.questionId]" style="margin-top:12px">
          <div v-for="opt in parseOptions(q.options)" :key="opt.key" style="margin:8px 0">
            <el-radio :value="opt.key">{{ opt.key }}. {{ opt.value }}</el-radio>
          </div>
        </el-radio-group>

        <!-- 多选题 -->
        <el-checkbox-group v-if="q.questionType === 2" v-model="multiAnswers[q.questionId]" style="margin-top:12px" @change="v => answers[q.questionId] = v.join(',')">
          <div v-for="opt in parseOptions(q.options)" :key="opt.key" style="margin:8px 0">
            <el-checkbox :value="opt.key">{{ opt.key }}. {{ opt.value }}</el-checkbox>
          </div>
        </el-checkbox-group>

        <!-- 判断题 -->
        <el-radio-group v-if="q.questionType === 3" v-model="answers[q.questionId]" style="margin-top:12px">
          <el-radio value="A">A. 正确</el-radio>
          <el-radio value="B">B. 错误</el-radio>
        </el-radio-group>

        <!-- 填空题 -->
        <el-input v-if="q.questionType === 4" v-model="answers[q.questionId]" placeholder="请输入答案" style="margin-top:12px" />

        <!-- 简答题 -->
        <el-input v-if="q.questionType === 5" v-model="answers[q.questionId]" type="textarea" :rows="4" placeholder="请输入答案" style="margin-top:12px" />
      </div>

      <div style="text-align:center;margin-top:30px">
        <el-button type="danger" size="large" @click="handleSubmit">交 卷</el-button>
      </div>
    </el-card>

    <el-dialog v-model="resultVisible" title="考试结果" width="600px">
      <div v-if="examResult" style="text-align:center">
        <div style="font-size:48px;font-weight:bold" :style="{ color: examResult.isPass ? '#67C23A' : '#F56C6C' }">
          {{ examResult.userScore }}
        </div>
        <div style="font-size:18px;margin:12px 0">
          {{ examResult.isPass ? '🎉 恭喜通过！' : '❌ 未通过' }}
        </div>
        <el-descriptions :column="2" border style="margin-top:20px">
          <el-descriptions-item label="总分">{{ examResult.totalScore }}</el-descriptions-item>
          <el-descriptions-item label="及格分">{{ examResult.passScore }}</el-descriptions-item>
          <el-descriptions-item label="得分">{{ examResult.userScore }}</el-descriptions-item>
          <el-descriptions-item label="用时">{{ Math.floor(examResult.duration / 60) }}分钟</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { startExam, submitExam } from '../../api/exam'
import { ElMessage, ElMessageBox } from 'element-plus'

const route = useRouter()
const router = useRouter()
const examId = ref(useRoute().params.id)
const examDetail = ref(null)
const answers = reactive({})
const multiAnswers = reactive({})
const resultVisible = ref(false)
const examResult = ref(null)

function parseOptions(str) {
  try { return JSON.parse(str) } catch { return [] }
}

function scrollToQuestion(idx) {
  const el = document.getElementById(`q-${idx}`)
  el?.scrollIntoView({ behavior: 'smooth' })
}

onMounted(async () => {
  try {
    const res = await startExam(examId.value)
    examDetail.value = res.data
  } catch (e) {}
})

async function handleSubmit() {
  await ElMessageBox.confirm('确定要交卷吗？', '提示', { type: 'warning' })
  const answerList = Object.entries(answers).map(([qid, ans]) => ({
    questionId: Number(qid),
    questionType: examDetail.value.questions.find(q => q.questionId === Number(qid))?.questionType,
    userAnswer: ans || ''
  }))
  try {
    const res = await submitExam({ examId: examId.value, answers: answerList })
    examResult.value = res.data
    resultVisible.value = true
  } catch (e) {}
}
</script>

<style scoped>
.question-nav { display: flex; flex-wrap: wrap; gap: 8px; margin-bottom: 20px; padding: 12px; background: #f5f7fa; border-radius: 8px; }
.q-nav-item { width: 36px; height: 36px; border-radius: 50%; background: #fff; border: 2px solid #ddd; display: flex; align-items: center; justify-content: center; cursor: pointer; font-size: 14px; }
.q-nav-item.answered { background: #409EFF; color: #fff; border-color: #409EFF; }
.question-item { padding: 20px 0; border-bottom: 1px solid #f0f0f0; }
</style>
