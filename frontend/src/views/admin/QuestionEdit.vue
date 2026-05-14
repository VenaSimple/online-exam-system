<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:20px">{{ isEdit ? '编辑题目' : '新增题目' }}</h3>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" style="max-width:700px">
        <el-form-item label="题目类型" prop="type">
          <el-select v-model="form.type" @change="handleTypeChange">
            <el-option label="单选题" :value="1" /><el-option label="多选题" :value="2" />
            <el-option label="判断题" :value="3" /><el-option label="填空题" :value="4" />
            <el-option label="简答题" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="题目分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" clearable style="width:100%">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="题目内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="3" placeholder="请输入题目内容" />
        </el-form-item>
        <el-form-item label="选项" v-if="form.type <= 3">
          <div v-for="(opt, idx) in options" :key="idx" style="margin:8px 0;display:flex;align-items:center;gap:8px">
            <span>{{ String.fromCharCode(65 + idx) }}.</span>
            <el-input v-model="opt.value" :placeholder="`选项${String.fromCharCode(65 + idx)}`" />
            <el-button v-if="options.length > 2" type="danger" :icon="Delete" circle size="small" @click="options.splice(idx, 1)" />
          </div>
          <el-button v-if="options.length < 6" @click="options.push({ key: String.fromCharCode(65 + options.length), value: '' })">添加选项</el-button>
        </el-form-item>
        <el-form-item label="正确答案" prop="answer">
          <el-input v-model="form.answer" :placeholder="form.type === 2 ? '多选用逗号分隔，如A,B' : '请输入正确答案'" />
        </el-form-item>
        <el-form-item label="难度">
          <el-select v-model="form.difficulty">
            <el-option label="简单" :value="1" /><el-option label="中等" :value="2" /><el-option label="困难" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="分值"><el-input-number v-model="form.score" :min="1" :max="100" /></el-form-item>
        <el-form-item label="解析"><el-input v-model="form.analysis" type="textarea" :rows="2" placeholder="答案解析" /></el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">保存</el-button>
          <el-button @click="router.back()">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getQuestionById, createQuestion, updateQuestion, getAllQuestionCategories } from '../../api/question'
import { ElMessage } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => !!route.params.id)
const formRef = ref(null)
const categories = ref([])
const options = ref([{ key: 'A', value: '' }, { key: 'B', value: '' }, { key: 'C', value: '' }, { key: 'D', value: '' }])

const form = reactive({ id: null, content: '', type: 1, answer: '', analysis: '', categoryId: null, difficulty: 1, score: 2 })
const rules = {
  content: [{ required: true, message: '请输入题目', trigger: 'blur' }],
  answer: [{ required: true, message: '请输入答案', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择题目分类', trigger: 'change' }]
}

function handleTypeChange(type) {
  if (type === 3) options.value = [{ key: 'A', value: '正确' }, { key: 'B', value: '错误' }]
  else if (type <= 2) options.value = [{ key: 'A', value: '' }, { key: 'B', value: '' }, { key: 'C', value: '' }, { key: 'D', value: '' }]
}

onMounted(async () => {
  const catRes = await getAllQuestionCategories()
  categories.value = catRes.data || []
  if (isEdit.value) {
    const res = await getQuestionById(route.params.id)
    Object.assign(form, res.data)
    if (res.data.options) {
      try { options.value = JSON.parse(res.data.options) } catch { options.value = [] }
    }
  }
})

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  form.options = JSON.stringify(options.value)
  if (isEdit.value) await updateQuestion(form); else await createQuestion(form)
  ElMessage.success('保存成功')
  router.push('/admin/questions')
}
</script>
