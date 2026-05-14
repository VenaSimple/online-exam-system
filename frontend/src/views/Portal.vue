<template>
  <div class="portal">
    <el-header class="portal-header">
      <div class="header-inner">
        <h1 class="logo">📚 在线培训考试系统</h1>
        <p class="slogan">学、练、考一体化在线学习平台</p>
        <div class="header-actions">
          <el-button type="primary" @click="router.push('/login')">登录</el-button>
          <el-button @click="router.push('/register')">注册</el-button>
        </div>
      </div>
    </el-header>

    <!-- 轮播图 -->
    <div class="banner-section">
      <el-carousel height="360px" v-if="banners.length">
        <el-carousel-item v-for="banner in banners" :key="banner.id">
          <div class="banner-item" :style="{ background: 'linear-gradient(135deg, #667eea, #764ba2)' }">
            <h2>{{ banner.title }}</h2>
          </div>
        </el-carousel-item>
      </el-carousel>
      <div v-else class="banner-default">
        <h1>在线培训考试系统</h1>
        <p>集"学、练、考"于一体，助力高效学习</p>
        <el-button type="primary" size="large" @click="router.push('/login')">立即开始学习</el-button>
      </div>
    </div>

    <!-- 热门课程 -->
    <div class="section">
      <div class="section-inner">
        <h2 class="section-title">热门课程</h2>
        <el-row :gutter="20">
          <el-col :span="6" v-for="course in courses" :key="course.id">
            <el-card shadow="hover" class="course-card">
              <div class="course-cover" style="background:linear-gradient(135deg,#409EFF,#764ba2);height:120px;display:flex;align-items:center;justify-content:center;color:#fff;font-size:22px">
                {{ course.title?.substring(0, 4) }}
              </div>
              <div style="padding:12px">
                <h4 style="margin:0 0 8px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">{{ course.title }}</h4>
                <div style="display:flex;justify-content:space-between;align-items:center">
                  <span style="color:#999;font-size:12px">{{ course.teacherName }}</span>
                  <el-tag :type="course.isFree ? 'success' : 'warning'" size="small">{{ course.isFree ? '免费' : '¥' + course.price }}</el-tag>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 通知公告 -->
    <div class="section" style="background:#f5f7fa">
      <div class="section-inner">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card>
              <template #header><h3>通知公告</h3></template>
              <div v-for="n in notices" :key="n.id" style="padding:10px 0;border-bottom:1px solid #f0f0f0">
                <el-tag size="small" :type="n.isTop ? 'danger' : 'info'" style="margin-right:8px">{{ n.isTop ? '置顶' : '通知' }}</el-tag>
                <span>{{ n.title }}</span>
              </div>
              <el-empty v-if="!notices.length" description="暂无公告" :image-size="60" />
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card>
              <template #header><h3>新闻资讯</h3></template>
              <div v-for="n in newsList" :key="n.id" style="padding:10px 0;border-bottom:1px solid #f0f0f0">
                <span>{{ n.title }}</span>
                <span style="float:right;color:#999;font-size:12px">{{ n.author }}</span>
              </div>
              <el-empty v-if="!newsList.length" description="暂无新闻" :image-size="60" />
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>

    <el-footer class="portal-footer">
      在线培训考试系统 © 2024 | 集学、练、考于一体的在线学习平台
    </el-footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getPortalBanners, getPortalCourses, getPortalNews, getPortalNotices } from '../../api/portal'

const router = useRouter()
const banners = ref([])
const courses = ref([])
const notices = ref([])
const newsList = ref([])

onMounted(async () => {
  try { const r = await getPortalBanners(); banners.value = r.data || [] } catch {}
  try { const r = await getPortalCourses({ pageNum: 1, pageSize: 8 }); courses.value = r.data?.rows || [] } catch {}
  try { const r = await getPortalNotices({ pageNum: 1, pageSize: 5 }); notices.value = r.data?.rows || [] } catch {}
  try { const r = await getPortalNews({ pageNum: 1, pageSize: 5 }); newsList.value = r.data?.rows || [] } catch {}
})
</script>

<style scoped>
.portal { background: #fff; }
.portal-header { background: #304156; }
.header-inner { max-width: 1200px; margin: 0 auto; display: flex; align-items: center; height: 60px; color: #fff; }
.header-inner .logo { font-size: 20px; margin: 0; }
.header-inner .slogan { margin: 0 20px; color: #bfcbd9; font-size: 14px; }
.header-actions { margin-left: auto; }
.banner-section { background: #f5f7fa; }
.banner-item { height: 360px; display: flex; align-items: center; justify-content: center; color: #fff; }
.banner-item h2 { font-size: 36px; }
.banner-default { height: 360px; display: flex; flex-direction: column; align-items: center; justify-content: center; background: linear-gradient(135deg, #667eea, #764ba2); color: #fff; }
.banner-default h1 { font-size: 42px; margin-bottom: 16px; }
.banner-default p { font-size: 18px; margin-bottom: 24px; }
.section { padding: 40px 0; }
.section-inner { max-width: 1200px; margin: 0 auto; }
.section-title { text-align: center; margin-bottom: 30px; font-size: 24px; }
.course-card { cursor: pointer; margin-bottom: 20px; }
.course-card:hover { transform: translateY(-4px); transition: all .3s; }
.portal-footer { text-align: center; color: #999; background: #304156; color: #bfcbd9; height: 60px; line-height: 60px; }
</style>
