<template>
  <div class="reservation-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>预订管理</span>
          <el-button type="primary" @click="handleAdd">新增预订</el-button>
        </div>
      </template>
      
      <el-table :data="reservations" style="width: 100%">
        <el-table-column prop="guestId" label="客户ID" />
        <el-table-column prop="roomId" label="房间ID" />
        <el-table-column prop="checkInDate" label="入住日期" />
        <el-table-column prop="checkOutDate" label="退房日期" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
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

    <!-- 新增/编辑预订对话框 -->
    <el-dialog :title="isEdit ? '编辑预订' : '新增预订'" v-model="dialogVisible" width="50%">
      <el-form :model="currentReservation" :rules="reservationRules" ref="reservationFormRef" label-width="100px">
        <el-form-item label="客户ID" prop="guestId">
          <el-input v-model="currentReservation.guestId" />
        </el-form-item>
        <el-form-item label="房间ID" prop="roomId">
          <el-input v-model="currentReservation.roomId" />
        </el-form-item>
        <el-form-item label="入住日期" prop="checkInDate">
          <el-date-picker v-model="currentReservation.checkInDate" type="date" />
        </el-form-item>
        <el-form-item label="退房日期" prop="checkOutDate">
          <el-date-picker v-model="currentReservation.checkOutDate" type="date" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="currentReservation.status" placeholder="请选择状态">
            <el-option label="待确认" :value="0" />
            <el-option label="已确认" :value="1" />
            <el-option label="已取消" :value="2" />
            <el-option label="已完成" :value="3" />
          </el-select>
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
import { getReservations, addReservation, updateReservation, deleteReservation } from '@/api/reservation'

const reservations = ref([])
const dialogVisible = ref(false)
const currentReservation = ref({})
const isEdit = ref(false)
const reservationRules = {
  guestId: [{ required: true, message: '请输入客户ID', trigger: 'blur' }],
  roomId: [{ required: true, message: '请输入房间ID', trigger: 'blur' }],
  checkInDate: [{ required: true, message: '请选择入住日期', trigger: 'change' }],
  checkOutDate: [{ required: true, message: '请选择退房日期', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
}

const fetchReservations = async () => {
  const response = await getReservations()
  reservations.value = response.data
}

const handleAdd = () => {
  currentReservation.value = { guestId: '', roomId: '', checkInDate: '', checkOutDate: '', status: 0 }
  isEdit.value = false
  dialogVisible.value = true // 打开对话框
}

const handleEdit = (row) => {
  currentReservation.value = { ...row }
  isEdit.value = true
  dialogVisible.value = true // 打开对话框
}

const handleSave = async () => {
  const formRef = ref(null)
  await formRef.value.validate(async (valid) => {
    if (valid) {
      if (isEdit.value) {
        await updateReservation(currentReservation.value.reservationId, currentReservation.value)
      } else {
        await addReservation(currentReservation.value)
      }
      dialogVisible.value = false // 关闭对话框
      fetchReservations() // 刷新预订列表
    }
  })
}

const handleDelete = async (row) => {
  await deleteReservation(row.reservationId)
  fetchReservations() // 刷新预订列表
}

const getStatusType = (status) => {
  const statusMap = {
    0: 'warning',
    1: 'success',
    2: 'danger',
    3: 'info'
  }
  return statusMap[status]
}

const getStatusText = (status) => {
  const statusMap = {
    0: '待确认',
    1: '已确认',
    2: '已取消',
    3: '已完成'
  }
  return statusMap[status]
}

onMounted(() => {
  fetchReservations()
})
</script>

<style lang="scss" scoped>
.reservation-container {
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

  .el-dialog {
    .el-form-item {
      margin-bottom: 20px; /* 增加输入框之间的间距 */
    }
  }
}
</style> 