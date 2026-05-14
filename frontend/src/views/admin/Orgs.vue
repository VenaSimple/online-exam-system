<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <h3>组织架构</h3>
        <el-button type="primary" @click="handleAdd">新增机构</el-button>
      </div>
      <el-table :data="orgs" row-key="id" border default-expand-all :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
        <el-table-column prop="name" label="机构名称" />
        <el-table-column prop="code" label="编码" width="120" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">{{ ['', '公司', '部门', '小组'][row.type] }}</template>
        </el-table-column>
        <el-table-column prop="leader" label="负责人" width="100" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }"><el-tag :type="row.status ? 'success' : 'danger'">{{ row.status ? '正常' : '禁用' }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="editForm.id ? '编辑' : '新增'" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="名称"><el-input v-model="editForm.name" /></el-form-item>
        <el-form-item label="编码"><el-input v-model="editForm.code" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="editForm.type"><el-option label="公司" :value="1" /><el-option label="部门" :value="2" /><el-option label="小组" :value="3" /></el-select></el-form-item>
        <el-form-item label="负责人"><el-input v-model="editForm.leader" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getOrgTree, createOrg, deleteOrg } from '../../api/system'
import { ElMessage, ElMessageBox } from 'element-plus'

const orgs = ref([])
const dialogVisible = ref(false)
const editForm = reactive({ id: null, name: '', code: '', type: 1, leader: '', parentId: 0 })

async function loadData() {
  const res = await getOrgTree()
  orgs.value = res.data || []
}

function handleAdd() { Object.assign(editForm, { id: null, name: '', code: '', type: 1, leader: '', parentId: 0 }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(editForm, row); dialogVisible.value = true }

async function handleSave() {
  await createOrg(editForm)
  ElMessage.success('保存成功'); dialogVisible.value = false; loadData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteOrg(id); ElMessage.success('删除成功'); loadData()
}

onMounted(loadData)
</script>
