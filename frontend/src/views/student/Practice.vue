<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:16px">试题练习</h3>
      <el-row :gutter="16" style="margin-bottom:16px">
        <el-col :span="6">
          <el-select v-model="query.type" placeholder="题目类型" clearable>
            <el-option label="单选题" :value="1" /><el-option label="多选题" :value="2" />
            <el-option label="判断题" :value="3" /><el-option label="填空题" :value="4" />
            <el-option label="简答题" :value="5" />
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-select v-model="query.difficulty" placeholder="难度" clearable>
            <el-option label="简单" :value="1" /><el-option label="中等" :value="2" /><el-option label="困难" :value="3" />
          </el-select>
        </el-col>
        <el-col :span="6"><el-button type="primary" @click="loadData">开始练习</el-button></el-col>
      </el-row>
      <div v-for="(q, idx) in questions" :key="q.id" class="practice-item">
        <h4>{{ idx + 1 }}. {{ q.content }} <el-tag size="small">{{ ['', '单选', '多选', '判断', '填空', '简答'][q.type] }}</el-tag></h4>
        <div v-if="q.type <= 3 && q.options" style="margin-top:8px">
          <div v-for="opt in parseOptions(q.options)" :key="opt.key" style="margin:4px 0">
            {{ opt.key }}. {{ opt.value }}
          </div>
        </div>
        <el-button size="small" style="margin-top:8px" @click="showAnswer(q)">查看答案</el-button>
        <div v-if="q._showAnswer" class="answer-reveal">
          <p><strong>答案:</strong> {{ q.answer }}</p>
          <p v-if="q.analysis"><strong>解析:</strong> {{ q.analysis }}</p>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getQuestionList } from '../../api/question'

const questions = ref([])
const query = reactive({ type: null, difficulty: null })

function parseOptions(str) { try { return JSON.parse(str) } catch { return [] } }

function showAnswer(q) { q._showAnswer = !q._showAnswer }

async function loadData() {
  const res = await getQuestionList({ pageNum: 1, pageSize: 20, type: query.type, difficulty: query.difficulty })
  questions.value = (res.data.rows || []).map(q => ({ ...q, _showAnswer: false }))
}

onMounted(loadData)
</script>

<style scoped>
.practice-item { padding: 16px; border: 1px solid #f0f0f0; border-radius: 8px; margin-bottom: 12px; }
.answer-reveal { margin-top: 12px; padding: 12px; background: #f0f9eb; border-radius: 6px; }
</style>
