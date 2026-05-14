<template>
  <el-card>
    <h3 style="margin-bottom:20px">系统配置</h3>
    <el-tabs v-model="activeGroup">
      <el-tab-pane label="基本设置" name="basic" />
      <el-tab-pane label="支付配置" name="payment" />
      <el-tab-pane label="短信配置" name="sms" />
      <el-tab-pane label="第三方登录" name="oauth" />
      <el-tab-pane label="成绩权重" name="score" />
    </el-tabs>
    <el-form label-width="160px" style="max-width:600px;margin-top:20px">
      <el-form-item v-for="cfg in filteredConfigs" :key="cfg.id" :label="cfg.configDesc || cfg.configKey">
        <el-input v-model="cfg.configValue" :type="cfg.configValue?.length > 100 ? 'textarea' : 'text'" :rows="3" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSave">保存配置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script setup>
import { ref, computed, onMounted } from 'vue'
import { getConfigList, updateConfig } from '../../api/portal'
import { ElMessage } from 'element-plus'
const configs = ref([])
const activeGroup = ref('basic')
const filteredConfigs = computed(() => configs.value.filter(c => c.configGroup === activeGroup.value))
async function loadData() { const r = await getConfigList(); configs.value = r.data || [] }
async function handleSave() {
  for (const cfg of filteredConfigs.value) { await updateConfig(cfg) }
  ElMessage.success('保存成功')
}
onMounted(loadData)
</script>
