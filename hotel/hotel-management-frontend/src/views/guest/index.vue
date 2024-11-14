<template>
  <div class="guest-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>客户管理</span>
          <el-button type="primary" @click="handleAdd">新增客户</el-button>
        </div>
      </template>
      
      <el-table :data="guests" style="width: 100%">
        <el-table-column prop="name" label="客户姓名" />
        <el-table-column prop="idNumber" label="证件号码" />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑客户对话框 -->
    <el-dialog :title="isEdit ? '编辑客户' : '新增客户'" v-model="dialogVisible" width="50%">
      <el-form :model="currentGuest" :rules="guestRules" ref="guestFormRef" label-width="100px">
        <el-form-item label="客户姓名" prop="name">
          <el-input v-model="currentGuest.name" />
        </el-form-item>
        <el-form-item label="证件类型" prop="idType">
          <el-select v-model="currentGuest.idType" placeholder="请选择证件类型">
            <el-option label="身份证" :value="1" />
            <el-option label="护照" :value="2" />
            <el-option label="军官证" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="证件号码" prop="idNumber">
          <el-input v-model="currentGuest.idNumber" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="currentGuest.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="currentGuest.email" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="currentGuest.address" type="textarea" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSave">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getGuests, addGuest, updateGuest, deleteGuest } from '@/api/guest'

const guests = ref([])
const dialogVisible = ref(false)
const currentGuest = ref({})
const isEdit = ref(false)
const guestRules = {
  name: [{ required: true, message: '请输入客户姓名', trigger: 'blur' }],
  idType: [{ required: true, message: '请选择证件类型', trigger: 'change' }],
  idNumber: [{ required: true, message: '请输入证件号码', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
}

const fetchGuests = async () => {
  const response = await getGuests()
  guests.value = response.data
}

const handleAdd = () => {
  currentGuest.value = { name: '', idType: null, idNumber: '', phone: '', email: '', address: '' }
  isEdit.value = false
  dialogVisible.value = true // 打开对话框
}

const handleEdit = (row) => {
  currentGuest.value = { ...row }
  isEdit.value = true
  dialogVisible.value = true // 打开对话框
}

const handleSave = async () => {
  const formRef = ref(null)
  await formRef.value.validate(async (valid) => {
    if (valid) {
      if (isEdit.value) {
        await updateGuest(currentGuest.value.guestId, currentGuest.value)
      } else {
        await addGuest(currentGuest.value)
      }
      dialogVisible.value = false // 关闭对话框
      fetchGuests() // 刷新客户列表
    }
  })
}

const handleDelete = async (row) => {
  await deleteGuest(row.guestId)
  fetchGuests() // 刷新客户列表
}

onMounted(() => {
  fetchGuests()
})
</script>

<style lang="scss" scoped>
.guest-container {
  padding: 20px;
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .dialog-footer {
    display: flex;
    justify-content: flex-end;
  }
}
</style> 