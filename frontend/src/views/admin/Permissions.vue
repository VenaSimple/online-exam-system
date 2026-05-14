<template>
  <el-card>
    <h3 style="margin-bottom:20px">权限管理</h3>
    <el-row :gutter="20">
      <el-col :span="8">
        <h4>角色选择</h4>
        <el-radio-group v-model="currentRole" @change="loadRolePermissions">
          <el-radio value="admin">管理员</el-radio>
          <el-radio value="teacher">教师</el-radio>
          <el-radio value="student">学生</el-radio>
        </el-radio-group>
      </el-col>
      <el-col :span="16">
        <h4>权限分配</h4>
        <el-tree ref="treeRef" :data="treeData" :props="{ label: 'name', children: 'children' }" show-checkbox node-key="id" default-expand-all />
        <el-button type="primary" style="margin-top:16px" @click="handleSave">保存权限</el-button>
      </el-col>
    </el-row>
  </el-card>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { getPermissionTree, getPermissionsByRole, assignPermissions } from '../../api/portal'
import { ElMessage } from 'element-plus'
const treeRef = ref()
const currentRole = ref('admin')
const treeData = ref([])
function buildTree(list, parentId = 0) {
  return list.filter(i => i.parentId === parentId).map(i => ({ ...i, children: buildTree(list, i.id) })).filter(i => i.children?.length || i.type === 2)
}
async function loadData() {
  const r = await getPermissionTree()
  treeData.value = buildTree(r.data || [])
}
async function loadRolePermissions() {
  const r = await getPermissionsByRole(currentRole.value)
  const ids = (r.data || []).map(p => p.id)
  treeRef.value?.setCheckedKeys(ids)
}
async function handleSave() {
  const checkedKeys = treeRef.value?.getCheckedKeys() || []
  await assignPermissions(currentRole.value, checkedKeys)
  ElMessage.success('保存成功')
}
onMounted(async () => { await loadData(); await loadRolePermissions() })
</script>
