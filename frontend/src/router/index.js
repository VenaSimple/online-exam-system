import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/portal',
    name: 'Portal',
    component: () => import('../views/Portal.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/login/Register.vue')
  },
  // 学生端
  {
    path: '/student',
    component: () => import('../layout/StudentLayout.vue'),
    meta: { role: 'student' },
    children: [
      { path: '', redirect: '/student/home' },
      { path: 'home', name: 'StudentHome', component: () => import('../views/student/Home.vue') },
      { path: 'courses', name: 'StudentCourses', component: () => import('../views/student/Courses.vue') },
      { path: 'course/:id', name: 'StudentCourseDetail', component: () => import('../views/student/CourseDetail.vue') },
      { path: 'learn/:courseId/:chapterId', name: 'StudentLearn', component: () => import('../views/student/Learn.vue') },
      { path: 'exams', name: 'StudentExams', component: () => import('../views/student/Exams.vue') },
      { path: 'exam/:id', name: 'StudentExamPage', component: () => import('../views/student/ExamPage.vue') },
      { path: 'exam/result/:recordId', name: 'StudentExamResult', component: () => import('../views/student/ExamResult.vue') },
      { path: 'practice', name: 'StudentPractice', component: () => import('../views/student/Practice.vue') },
      { path: 'knowledge', name: 'StudentKnowledge', component: () => import('../views/student/Knowledge.vue') },
      { path: 'records', name: 'StudentRecords', component: () => import('../views/student/Records.vue') },
      { path: 'profile', name: 'StudentProfile', component: () => import('../views/student/Profile.vue') },
    ]
  },
  // 教师端
  {
    path: '/teacher',
    component: () => import('../layout/TeacherLayout.vue'),
    meta: { role: 'teacher' },
    children: [
      { path: '', redirect: '/teacher/home' },
      { path: 'home', name: 'TeacherHome', component: () => import('../views/teacher/Home.vue') },
      { path: 'courses', name: 'TeacherCourses', component: () => import('../views/teacher/Courses.vue') },
      { path: 'course/edit/:id?', name: 'TeacherCourseEdit', component: () => import('../views/teacher/CourseEdit.vue') },
      { path: 'questions', name: 'TeacherQuestions', component: () => import('../views/teacher/Questions.vue') },
      { path: 'question/edit/:id?', name: 'TeacherQuestionEdit', component: () => import('../views/teacher/QuestionEdit.vue') },
      { path: 'papers', name: 'TeacherPapers', component: () => import('../views/teacher/Papers.vue') },
      { path: 'paper/edit/:id?', name: 'TeacherPaperEdit', component: () => import('../views/teacher/PaperEdit.vue') },
      { path: 'exams', name: 'TeacherExams', component: () => import('../views/teacher/Exams.vue') },
      { path: 'exam/edit/:id?', name: 'TeacherExamEdit', component: () => import('../views/teacher/ExamEdit.vue') },
      { path: 'records', name: 'TeacherRecords', component: () => import('../views/teacher/Records.vue') },
      { path: 'knowledge', name: 'TeacherKnowledge', component: () => import('../views/teacher/Knowledge.vue') },
    ]
  },
  // 管理端
  {
    path: '/admin',
    component: () => import('../layout/AdminLayout.vue'),
    meta: { role: 'admin' },
    children: [
      { path: '', redirect: '/admin/dashboard' },
      { path: 'dashboard', name: 'AdminDashboard', component: () => import('../views/admin/Dashboard.vue') },
      { path: 'users', name: 'AdminUsers', component: () => import('../views/admin/Users.vue') },
      { path: 'courses', name: 'AdminCourses', component: () => import('../views/admin/Courses.vue') },
      { path: 'questions', name: 'AdminQuestions', component: () => import('../views/admin/Questions.vue') },
      { path: 'papers', name: 'AdminPapers', component: () => import('../views/admin/Papers.vue') },
      { path: 'exams', name: 'AdminExams', component: () => import('../views/admin/Exams.vue') },
      { path: 'records', name: 'AdminRecords', component: () => import('../views/admin/Records.vue') },
      { path: 'notices', name: 'AdminNotices', component: () => import('../views/admin/Notices.vue') },
      { path: 'news', name: 'AdminNews', component: () => import('../views/admin/News.vue') },
      { path: 'orgs', name: 'AdminOrgs', component: () => import('../views/admin/Orgs.vue') },
      { path: 'knowledge', name: 'AdminKnowledge', component: () => import('../views/admin/Knowledge.vue') },
      { path: 'banners', name: 'AdminBanners', component: () => import('../views/admin/Banners.vue') },
      { path: 'config', name: 'AdminConfig', component: () => import('../views/admin/Config.vue') },
      { path: 'majors', name: 'AdminMajors', component: () => import('../views/admin/Majors.vue') },
      { path: 'permissions', name: 'AdminPermissions', component: () => import('../views/admin/Permissions.vue') },
      { path: 'student-courses', name: 'AdminStudentCourses', component: () => import('../views/admin/StudentCourses.vue') },
    ]
  },
  { path: '/', redirect: '/portal' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')

  // 门户页面无需登录
  if (to.path === '/portal' || to.path === '/login' || to.path === '/register') {
    next()
  } else if (!token) {
    next('/portal')
  } else {
    const requiredRole = to.matched.find(r => r.meta.role)?.meta?.role
    if (requiredRole && role !== requiredRole && role !== 'admin') {
      if (role === 'admin') {
        next()
      } else {
        next(`/${role}`)
      }
    } else {
      next()
    }
  }
})

export default router
