<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:20px">
        <h3 style="margin:0">{{ isEdit ? '编辑课程' : '新增课程' }}</h3>
        <el-button @click="router.back()">返回列表</el-button>
      </div>
      <el-tabs v-model="activeTab" type="border-card">
        <!-- ==================== 基本信息 ==================== -->
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="basicFormRef" :model="basicForm" :rules="basicRules" label-width="100px" style="max-width:700px">
            <el-form-item label="课程名称" prop="title">
              <el-input v-model="basicForm.title" placeholder="请输入课程名称" />
            </el-form-item>
            <el-form-item label="课程分类" prop="categoryId">
              <el-select v-model="basicForm.categoryId" placeholder="请选择分类" clearable style="width:100%">
                <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
              </el-select>
            </el-form-item>
            <el-form-item label="封面图">
              <el-input v-model="basicForm.cover" placeholder="输入封面图URL" />
            </el-form-item>
            <el-form-item label="是否免费">
              <el-switch v-model="basicForm.isFree" :active-value="1" :inactive-value="0" />
            </el-form-item>
            <el-form-item label="价格" v-if="basicForm.isFree === 0">
              <el-input-number v-model="basicForm.price" :min="0" :precision="2" />
            </el-form-item>
            <el-form-item label="原价" v-if="basicForm.isFree === 0">
              <el-input-number v-model="basicForm.originalPrice" :min="0" :precision="2" />
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="basicForm.status">
                <el-option label="草稿" :value="0" /><el-option label="已发布" :value="1" /><el-option label="下架" :value="2" />
              </el-select>
            </el-form-item>
            <el-form-item label="标签">
              <el-input v-model="basicForm.tags" placeholder="用逗号分隔" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSaveBasic">保存基本信息</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <!-- ==================== 简介 ==================== -->
        <el-tab-pane label="简介" name="intro">
          <el-input v-model="introForm.description" type="textarea" :rows="12" placeholder="请输入课程简介" />
          <div style="margin-top:16px">
            <el-button type="primary" @click="handleSaveIntro">保存简介</el-button>
          </div>
        </el-tab-pane>

        <!-- ==================== 章节 ==================== -->
        <el-tab-pane label="章节" name="chapters">
          <div style="margin-bottom:16px">
            <el-button type="primary" @click="openChapterDialog(null)">新增章节</el-button>
          </div>
          <el-table :data="chapters" stripe row-key="id" border>
            <el-table-column prop="title" label="章节名称" />
            <el-table-column prop="type" label="类型" width="100">
              <template #default="{ row }"><el-tag :type="row.type === 1 ? '' : 'success'">{{ row.type === 1 ? '章节' : '课时' }}</el-tag></template>
            </el-table-column>
            <el-table-column prop="sort" label="排序" width="80" />
            <el-table-column prop="isFree" label="免费试看" width="100">
              <template #default="{ row }">{{ row.isFree === 1 ? '是' : '否' }}</template>
            </el-table-column>
            <el-table-column label="操作" width="180">
              <template #default="{ row }">
                <el-button size="small" @click="openChapterDialog(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDeleteChapter(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-dialog v-model="chapterDialogVisible" :title="editingChapter.id ? '编辑章节' : '新增章节'" width="500px">
            <el-form :model="editingChapter" label-width="80px">
              <el-form-item label="章节名称"><el-input v-model="editingChapter.title" /></el-form-item>
              <el-form-item label="类型">
                <el-select v-model="editingChapter.type"><el-option label="章节" :value="1" /><el-option label="课时" :value="2" /></el-select>
              </el-form-item>
              <el-form-item label="排序"><el-input-number v-model="editingChapter.sort" :min="0" /></el-form-item>
              <el-form-item label="视频地址"><el-input v-model="editingChapter.mediaUrl" placeholder="视频/文档URL" /></el-form-item>
              <el-form-item label="时长(秒)"><el-input-number v-model="editingChapter.duration" :min="0" /></el-form-item>
              <el-form-item label="免费试看"><el-switch v-model="editingChapter.isFree" :active-value="1" :inactive-value="0" /></el-form-item>
            </el-form>
            <template #footer>
              <el-button @click="chapterDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="handleSaveChapter">保存</el-button>
            </template>
          </el-dialog>
        </el-tab-pane>

        <!-- ==================== 试题 ==================== -->
        <el-tab-pane label="试题" name="questions">
          <el-tabs v-model="questionType" type="card" @tab-change="loadQuestions">
            <el-tab-pane label="单选题" :name="1" />
            <el-tab-pane label="多选题" :name="2" />
            <el-tab-pane label="判断题" :name="3" />
            <el-tab-pane label="填空题" :name="4" />
            <el-tab-pane label="简答题" :name="5" />
          </el-tabs>
          <div style="margin:12px 0;display:flex;gap:8px">
            <el-button type="primary" @click="openQuestionDialog(null)">新增{{ questionTypeLabel }}</el-button>
          </div>
          <el-table :data="questions" stripe>
            <el-table-column prop="content" label="题目内容" show-overflow-tooltip />
            <el-table-column prop="difficulty" label="难度" width="80">
              <template #default="{ row }"><el-tag :type="['','success','warning','danger'][row.difficulty]">{{ ['', '简单', '中等', '困难'][row.difficulty] }}</el-tag></template>
            </el-table-column>
            <el-table-column prop="score" label="分值" width="60" />
            <el-table-column label="操作" width="180">
              <template #default="{ row }">
                <el-button size="small" @click="openQuestionDialog(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDeleteQuestion(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-dialog v-model="questionDialogVisible" :title="editingQuestion.id ? '编辑题目' : '新增题目'" width="600px">
            <el-form :model="editingQuestion" label-width="80px">
              <el-form-item label="题目内容"><el-input v-model="editingQuestion.content" type="textarea" :rows="3" /></el-form-item>
              <el-form-item label="选项" v-if="editingQuestion.type <= 3">
                <div v-for="(opt, idx) in questionOptions" :key="idx" style="margin:6px 0;display:flex;gap:8px;align-items:center">
                  <span style="width:20px">{{ String.fromCharCode(65 + idx) }}.</span>
                  <el-input v-model="opt.value" style="flex:1" />
                  <el-button v-if="questionOptions.length > 2" type="danger" :icon="Delete" circle size="small" @click="questionOptions.splice(idx, 1)" />
                </div>
                <el-button v-if="questionOptions.length < 6" size="small" @click="questionOptions.push({ key: String.fromCharCode(65 + questionOptions.length), value: '' })">添加选项</el-button>
              </el-form-item>
              <el-form-item label="正确答案"><el-input v-model="editingQuestion.answer" :placeholder="editingQuestion.type === 2 ? '多选用逗号分隔' : '请输入答案'" /></el-form-item>
              <el-form-item label="难度">
                <el-select v-model="editingQuestion.difficulty"><el-option label="简单" :value="1" /><el-option label="中等" :value="2" /><el-option label="困难" :value="3" /></el-select>
              </el-form-item>
              <el-form-item label="分值"><el-input-number v-model="editingQuestion.score" :min="1" :max="100" /></el-form-item>
              <el-form-item label="解析"><el-input v-model="editingQuestion.analysis" type="textarea" :rows="2" /></el-form-item>
            </el-form>
            <template #footer>
              <el-button @click="questionDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="handleSaveQuestion">保存</el-button>
            </template>
          </el-dialog>
        </el-tab-pane>

        <!-- ==================== 试卷/考试 ==================== -->
        <el-tab-pane label="试卷/考试" name="exams">
          <h4>试卷列表</h4>
          <el-table :data="papers" stripe style="margin-bottom:24px">
            <el-table-column prop="title" label="试卷名称" />
            <el-table-column prop="totalScore" label="总分" width="80" />
            <el-table-column prop="questionCount" label="题目数" width="80" />
            <el-table-column prop="duration" label="时长(分)" width="90" />
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '草稿' }}</el-tag></template>
            </el-table-column>
          </el-table>
          <h4>考试列表</h4>
          <el-table :data="exams" stripe>
            <el-table-column prop="title" label="考试名称" />
            <el-table-column prop="examType" label="类型" width="100">
              <template #default="{ row }">{{ ['', '正式', '模拟', '练习'][row.examType] }}</template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }"><el-tag :type="['info','success','warning','danger'][row.status]">{{ ['草稿','已发布','进行中','已结束'][row.status] }}</el-tag></template>
            </el-table-column>
            <el-table-column prop="startTime" label="开始时间" width="180" />
            <el-table-column prop="endTime" label="结束时间" width="180" />
          </el-table>
        </el-tab-pane>

        <!-- ==================== 知识点 ==================== -->
        <el-tab-pane label="知识点" name="knowledge">
          <div style="margin-bottom:16px">
            <el-button type="primary" @click="openKnowledgeDialog(null)">新增知识点</el-button>
          </div>
          <el-table :data="knowledges" stripe>
            <el-table-column prop="title" label="标题" />
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '已发布' : '草稿' }}</el-tag></template>
            </el-table-column>
            <el-table-column prop="viewCount" label="浏览量" width="80" />
            <el-table-column label="操作" width="180">
              <template #default="{ row }">
                <el-button size="small" @click="openKnowledgeDialog(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDeleteKnowledge(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-dialog v-model="knowledgeDialogVisible" :title="editingKnowledge.id ? '编辑知识点' : '新增知识点'" width="600px">
            <el-form :model="editingKnowledge" label-width="80px">
              <el-form-item label="标题"><el-input v-model="editingKnowledge.title" /></el-form-item>
              <el-form-item label="内容"><el-input v-model="editingKnowledge.content" type="textarea" :rows="6" /></el-form-item>
              <el-form-item label="状态">
                <el-select v-model="editingKnowledge.status"><el-option label="草稿" :value="0" /><el-option label="已发布" :value="1" /></el-select>
              </el-form-item>
              <el-form-item label="标签"><el-input v-model="editingKnowledge.tags" placeholder="逗号分隔" /></el-form-item>
            </el-form>
            <template #footer>
              <el-button @click="knowledgeDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="handleSaveKnowledge">保存</el-button>
            </template>
          </el-dialog>
        </el-tab-pane>

        <!-- ==================== 公告 ==================== -->
        <el-tab-pane label="公告" name="notices">
          <div style="margin-bottom:16px">
            <el-button type="primary" @click="openNoticeDialog(null)">新增公告</el-button>
          </div>
          <el-table :data="notices" stripe>
            <el-table-column prop="title" label="标题" />
            <el-table-column prop="type" label="类型" width="80">
              <template #default="{ row }"><el-tag :type="row.type === 1 ? '' : 'warning'">{{ row.type === 1 ? '通知' : '公告' }}</el-tag></template>
            </el-table-column>
            <el-table-column prop="isTop" label="置顶" width="80">
              <template #default="{ row }">{{ row.isTop === 1 ? '是' : '否' }}</template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '已发布' : '草稿' }}</el-tag></template>
            </el-table-column>
            <el-table-column label="操作" width="180">
              <template #default="{ row }">
                <el-button size="small" @click="openNoticeDialog(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDeleteNotice(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-dialog v-model="noticeDialogVisible" :title="editingNotice.id ? '编辑公告' : '新增公告'" width="600px">
            <el-form :model="editingNotice" label-width="80px">
              <el-form-item label="标题"><el-input v-model="editingNotice.title" /></el-form-item>
              <el-form-item label="类型">
                <el-select v-model="editingNotice.type"><el-option label="通知" :value="1" /><el-option label="公告" :value="2" /></el-select>
              </el-form-item>
              <el-form-item label="内容"><el-input v-model="editingNotice.content" type="textarea" :rows="6" /></el-form-item>
              <el-form-item label="置顶"><el-switch v-model="editingNotice.isTop" :active-value="1" :inactive-value="0" /></el-form-item>
              <el-form-item label="状态">
                <el-select v-model="editingNotice.status"><el-option label="草稿" :value="0" /><el-option label="已发布" :value="1" /></el-select>
              </el-form-item>
            </el-form>
            <template #footer>
              <el-button @click="noticeDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="handleSaveNotice">保存</el-button>
            </template>
          </el-dialog>
        </el-tab-pane>

        <!-- ==================== 留言 ==================== -->
        <el-tab-pane label="留言" name="messages">
          <el-table :data="messages" stripe>
            <el-table-column prop="userName" label="留言人" width="100" />
            <el-table-column prop="content" label="内容" show-overflow-tooltip />
            <el-table-column prop="replyUserName" label="回复" width="100" />
            <el-table-column prop="createTime" label="时间" width="180" />
            <el-table-column label="操作" width="160">
              <template #default="{ row }">
                <el-button v-if="row.status === 1" size="small" type="warning" @click="handleToggleMessage(row.id, 0)">隐藏</el-button>
                <el-button v-else size="small" type="success" @click="handleToggleMessage(row.id, 1)">显示</el-button>
                <el-button size="small" type="danger" @click="handleDeleteMessage(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="margin-top:16px;display:flex;gap:8px;align-items:center">
            <el-input v-model="newMessage" placeholder="回复留言" style="width:400px" />
            <el-button type="primary" @click="handleSendMessage">发送</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'
import {
  getCourseById, createCourse, updateCourse, getAllCourseCategories,
  getChapters, createChapter, updateChapter, deleteChapter,
  getMessages, createMessage, deleteMessage as deleteMsgApi, updateMessageStatus
} from '../../api/course'
import {
  getQuestionList, createQuestion, updateQuestion, deleteQuestion,
  getAllQuestionCategories
} from '../../api/question'
import { getPaperList } from '../../api/exam'
import { getExamList } from '../../api/exam'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => !!route.params.id)
const courseId = computed(() => Number(route.params.id))

// ========== 基本信息 ==========
const activeTab = ref('basic')
const categories = ref([])
const basicFormRef = ref(null)
const basicForm = reactive({
  id: null, title: '', categoryId: null, cover: '', description: '',
  isFree: 1, price: 0, originalPrice: 0, status: 0, tags: ''
})
const basicRules = {
  title: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择课程分类', trigger: 'change' }]
}

const introForm = reactive({ description: '' })

async function handleSaveBasic() {
  const valid = await basicFormRef.value.validate().catch(() => false)
  if (!valid) return
  if (isEdit.value) { await updateCourse(basicForm) } else {
    const res = await createCourse(basicForm)
    // 新建后跳转到编辑模式
    const newId = res.data?.id || res.data
    if (newId) { router.replace(`/teacher/course/edit/${newId}`); return }
  }
  ElMessage.success('保存成功')
}

async function handleSaveIntro() {
  if (!isEdit.value) { ElMessage.warning('请先保存基本信息'); return }
  await updateCourse({ id: basicForm.id, description: introForm.description })
  ElMessage.success('简介已保存')
}

// ========== 章节 ==========
const chapters = ref([])
const chapterDialogVisible = ref(false)
const editingChapter = reactive({ id: null, courseId: null, title: '', parentId: 0, sort: 0, type: 1, mediaUrl: '', duration: 0, isFree: 0 })

function openChapterDialog(row) {
  if (row) {
    Object.assign(editingChapter, row)
  } else {
    Object.assign(editingChapter, { id: null, courseId: courseId.value, title: '', parentId: 0, sort: 0, type: 1, mediaUrl: '', duration: 0, isFree: 0 })
  }
  chapterDialogVisible.value = true
}

async function handleSaveChapter() {
  editingChapter.courseId = courseId.value
  if (editingChapter.id) { await updateChapter({ ...editingChapter }) } else { await createChapter({ ...editingChapter }) }
  ElMessage.success('保存成功')
  chapterDialogVisible.value = false
  loadChapters()
}

async function handleDeleteChapter(id) {
  await ElMessageBox.confirm('确定删除该章节？', '提示', { type: 'warning' })
  await deleteChapter(id)
  ElMessage.success('删除成功')
  loadChapters()
}

// ========== 试题 ==========
const questionType = ref(1)
const questionTypeLabel = computed(() => ['', '单选题', '多选题', '判断题', '填空题', '简答题'][questionType.value])
const questions = ref([])
const questionDialogVisible = ref(false)
const questionOptions = ref([])
const editingQuestion = reactive({ id: null, content: '', type: 1, answer: '', analysis: '', categoryId: null, courseId: null, difficulty: 1, score: 2 })

function openQuestionDialog(row) {
  if (row) {
    Object.assign(editingQuestion, row)
    try { questionOptions.value = JSON.parse(row.options) || [] } catch { questionOptions.value = [] }
  } else {
    Object.assign(editingQuestion, { id: null, content: '', type: questionType.value, answer: '', analysis: '', categoryId: null, courseId: courseId.value, difficulty: 1, score: 2 })
    if (questionType.value === 3) {
      questionOptions.value = [{ key: 'A', value: '正确' }, { key: 'B', value: '错误' }]
    } else if (questionType.value <= 2) {
      questionOptions.value = [{ key: 'A', value: '' }, { key: 'B', value: '' }, { key: 'C', value: '' }, { key: 'D', value: '' }]
    } else {
      questionOptions.value = []
    }
  }
  questionDialogVisible.value = true
}

async function handleSaveQuestion() {
  editingQuestion.courseId = courseId.value
  editingQuestion.type = questionType.value
  const data = { ...editingQuestion, options: JSON.stringify(questionOptions.value) }
  if (editingQuestion.id) { await updateQuestion(data) } else { await createQuestion(data) }
  ElMessage.success('保存成功')
  questionDialogVisible.value = false
  loadQuestions()
}

async function handleDeleteQuestion(id) {
  await ElMessageBox.confirm('确定删除该题目？', '提示', { type: 'warning' })
  await deleteQuestion(id)
  ElMessage.success('删除成功')
  loadQuestions()
}

// ========== 试卷/考试 ==========
const papers = ref([])
const exams = ref([])

// ========== 知识点 ==========
const knowledges = ref([])
const knowledgeDialogVisible = ref(false)
const editingKnowledge = reactive({ id: null, title: '', content: '', status: 0, tags: '', courseId: null })

function openKnowledgeDialog(row) {
  if (row) { Object.assign(editingKnowledge, row) }
  else { Object.assign(editingKnowledge, { id: null, title: '', content: '', status: 0, tags: '', courseId: courseId.value }) }
  knowledgeDialogVisible.value = true
}

async function handleSaveKnowledge() {
  editingKnowledge.courseId = courseId.value
  const api = editingKnowledge.id
    ? (await import('../../api/system')).updateKnowledge(editingKnowledge)
    : (await import('../../api/system')).createKnowledge(editingKnowledge)
  ElMessage.success('保存成功')
  knowledgeDialogVisible.value = false
  loadKnowledges()
}

async function handleDeleteKnowledge(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  const { deleteKnowledge } = await import('../../api/system')
  await deleteKnowledge(id)
  ElMessage.success('删除成功')
  loadKnowledges()
}

// ========== 公告 ==========
const notices = ref([])
const noticeDialogVisible = ref(false)
const editingNotice = reactive({ id: null, title: '', content: '', type: 1, isTop: 0, status: 1, courseId: null })

function openNoticeDialog(row) {
  if (row) { Object.assign(editingNotice, row) }
  else { Object.assign(editingNotice, { id: null, title: '', content: '', type: 1, isTop: 0, status: 1, courseId: courseId.value }) }
  noticeDialogVisible.value = true
}

async function handleSaveNotice() {
  editingNotice.courseId = courseId.value
  const api = editingNotice.id
    ? (await import('../../api/system')).updateNotice(editingNotice)
    : (await import('../../api/system')).createNotice(editingNotice)
  ElMessage.success('保存成功')
  noticeDialogVisible.value = false
  loadNotices()
}

async function handleDeleteNotice(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  const { deleteNotice } = await import('../../api/system')
  await deleteNotice(id)
  ElMessage.success('删除成功')
  loadNotices()
}

// ========== 留言 ==========
const messages = ref([])
const newMessage = ref('')

async function handleSendMessage() {
  if (!newMessage.value.trim()) return
  await createMessage({ courseId: courseId.value, content: newMessage.value.trim() })
  newMessage.value = ''
  ElMessage.success('发送成功')
  loadMessages()
}

async function handleDeleteMessage(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteMsgApi(id)
  ElMessage.success('删除成功')
  loadMessages()
}

async function handleToggleMessage(id, status) {
  await updateMessageStatus(id, status)
  ElMessage.success('操作成功')
  loadMessages()
}

// ========== 数据加载 ==========
async function loadChapters() {
  if (!isEdit.value) return
  const res = await getChapters(courseId.value)
  chapters.value = res.data || []
}

async function loadQuestions() {
  if (!isEdit.value) return
  const res = await getQuestionList({ pageNum: 1, pageSize: 100, type: questionType.value, courseId: courseId.value })
  questions.value = res.data?.rows || []
}

async function loadPapersAndExams() {
  if (!isEdit.value) return
  const [pRes, eRes] = await Promise.all([
    getPaperList({ pageNum: 1, pageSize: 100, courseId: courseId.value }),
    getExamList({ pageNum: 1, pageSize: 100, courseId: courseId.value })
  ])
  papers.value = pRes.data?.rows || []
  exams.value = eRes.data?.rows || []
}

async function loadKnowledges() {
  if (!isEdit.value) return
  const { getKnowledgeList } = await import('../../api/system')
  const res = await getKnowledgeList({ pageNum: 1, pageSize: 100, courseId: courseId.value })
  knowledges.value = res.data?.rows || []
}

async function loadNotices() {
  if (!isEdit.value) return
  const { getNoticeList } = await import('../../api/system')
  const res = await getNoticeList({ pageNum: 1, pageSize: 100, courseId: courseId.value })
  notices.value = res.data?.rows || []
}

async function loadMessages() {
  if (!isEdit.value) return
  const res = await getMessages(courseId.value)
  messages.value = res.data || []
}

onMounted(async () => {
  const catRes = await getAllCourseCategories()
  categories.value = catRes.data || []

  if (isEdit.value) {
    const res = await getCourseById(courseId.value)
    Object.assign(basicForm, res.data)
    introForm.description = res.data.description || ''
    // 加载各页签数据
    loadChapters()
    loadQuestions()
    loadPapersAndExams()
    loadKnowledges()
    loadNotices()
    loadMessages()
  }
})
</script>
