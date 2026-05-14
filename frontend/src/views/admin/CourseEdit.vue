<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:20px">{{ isEdit ? '编辑课程' : '新增课程' }}</h3>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" style="max-width:700px">
        <el-form-item label="课程名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" clearable style="width:100%">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="封面图">
          <el-input v-model="form.cover" placeholder="输入封面图URL" />
        </el-form-item>
        <el-form-item label="课程描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="4" placeholder="请输入课程描述" />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-switch v-model="form.isFree" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="价格" v-if="form.isFree === 0">
          <el-input-number v-model="form.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="原价" v-if="form.isFree === 0">
          <el-input-number v-model="form.originalPrice" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="草稿" :value="0" /><el-option label="已发布" :value="1" /><el-option label="下架" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-input v-model="form.tags" placeholder="用逗号分隔" />
        </el-form-item>
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
import { getCourseById, createCourse, updateCourse, getAllCourseCategories } from '../../api/course'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => !!route.params.id)
const formRef = ref(null)
const categories = ref([])
const form = reactive({
  id: null, title: '', categoryId: null, cover: '', description: '',
  isFree: 1, price: 0, originalPrice: 0, status: 0, tags: ''
})
const rules = {
  title: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择课程分类', trigger: 'change' }]
}

onMounted(async () => {
  const catRes = await getAllCourseCategories()
  categories.value = catRes.data || []
  if (isEdit.value) {
    const res = await getCourseById(route.params.id)
    Object.assign(form, res.data)
  }
})

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  if (isEdit.value) { await updateCourse(form) } else { await createCourse(form) }
  ElMessage.success('保存成功')
  router.push('/admin/courses')
}
</script>
