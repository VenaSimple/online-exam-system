<template>
  <div v-if="result">
    <el-card>
      <div style="text-align:center;margin-bottom:24px">
        <div style="font-size:56px;font-weight:bold" :style="{ color: result.isPass ? '#67C23A' : '#F56C6C' }">
          {{ result.userScore }}
        </div>
        <div style="font-size:18px;margin:8px 0">{{ result.isPass ? '🎉 恭喜通过！' : '❌ 未通过' }}</div>
      </div>
      <div v-for="(ans, idx) in result.answers" :key="idx" class="answer-item">
        <h4>{{ idx + 1 }}. {{ ans.questionContent }}</h4>
        <p>你的答案: <span :style="{ color: ans.isCorrect === 1 ? '#67C23A' : '#F56C6C' }">{{ ans.userAnswer || '未作答' }}</span></p>
        <p>正确答案: <span style="color:#67C23A">{{ ans.correctAnswer }}</span></p>
        <p v-if="ans.analysis">解析: {{ ans.analysis }}</p>
        <el-divider />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getExamResult } from '../../api/exam'

const route = useRoute()
const result = ref(null)

onMounted(async () => {
  const res = await getExamResult(route.params.recordId)
  result.value = res.data
})
</script>

<style scoped>
.answer-item { padding: 16px 0; }
</style>
