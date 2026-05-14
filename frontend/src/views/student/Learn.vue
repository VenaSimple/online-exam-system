<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="18">
        <el-card>
          <div class="video-area">
            <div class="video-placeholder">
              <el-icon :size="64"><VideoPlay /></el-icon>
              <p>视频播放区域</p>
            </div>
          </div>
          <div style="margin-top:16px">
            <h3>{{ chapterTitle }}</h3>
            <el-progress :percentage="progress" :stroke-width="10" style="margin-top:12px" />
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <h4>课程目录</h4>
          <el-tree :data="chapters" :props="{ label: 'title', children: 'children' }" style="margin-top:12px" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const chapterTitle = ref('课程学习')
const progress = ref(0)
const chapters = ref([])

onMounted(() => {
  progress.value = Math.floor(Math.random() * 80) + 20
  chapters.value = [
    { title: '第一章', children: [{ title: '1.1 概述' }, { title: '1.2 详解' }] },
    { title: '第二章', children: [{ title: '2.1 理论' }, { title: '2.2 实践' }] }
  ]
})
</script>

<style scoped>
.video-area { background: #000; border-radius: 8px; overflow: hidden; }
.video-placeholder { height: 400px; display: flex; flex-direction: column; align-items: center; justify-content: center; color: #666; }
</style>
