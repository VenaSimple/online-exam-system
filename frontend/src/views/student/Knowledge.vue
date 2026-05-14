<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>知识库</h3>
        <el-input v-model="keyword" placeholder="搜索知识" style="width:300px" @keyup.enter="loadData" clearable />
      </div>
      <el-row :gutter="16">
        <el-col :span="8" v-for="item in list" :key="item.id">
          <el-card shadow="hover" style="margin-bottom:16px;cursor:pointer">
            <h4>{{ item.title }}</h4>
            <p style="color:#999;font-size:13px;margin:8px 0">{{ item.summary }}</p>
            <div style="display:flex;justify-content:space-between;color:#999;font-size:12px">
              <span>{{ item.authorName }}</span>
              <span>{{ item.viewCount }}次浏览</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getKnowledgeList } from '../../api/system'

const list = ref([])
const keyword = ref('')

async function loadData() {
  const res = await getKnowledgeList({ pageNum: 1, pageSize: 12, keyword: keyword.value })
  list.value = res.data.rows || []
}

onMounted(loadData)
</script>
