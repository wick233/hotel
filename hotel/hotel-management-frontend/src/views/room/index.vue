<template>
  <div class="room-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>房间管理</span>
          <el-button type="primary" @click="handleAdd">新增房间</el-button>
        </div>
      </template>
      
      <el-table :data="rooms" style="width: 100%">
        <el-table-column prop="roomNumber" label="房间号" />
        <el-table-column prop="roomType" label="房间类型" />
        <el-table-column prop="floor" label="楼层" />
        <el-table-column prop="price" label="价格" />
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

    <!-- 新增/编辑房间对话框 -->
    <el-dialog :title="isEdit ? '编辑房间' : '新增房间'" v-model="dialogVisible" width="50%">
      <el-form :model="currentRoom" :rules="roomRules" ref="roomFormRef" label-width="100px">
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="currentRoom.roomNumber" />
        </el-form-item>
        <el-form-item label="房间类型" prop="roomTypeId">
          <el-select v-model="currentRoom.roomTypeId" placeholder="请选择房间类型">
            <el-option v-for="type in roomTypes" :key="type.type_id" :label="type.type_name" :value="type.type_id" />
          </el-select>
        </el-form-item>
        <el-form-item label="楼层" prop="floor">
          <el-input v-model="currentRoom.floor" type="number" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="currentRoom.price" type="number" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="currentRoom.description" type="textarea" />
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
import { getRooms, addRoom, updateRoom, deleteRoom } from '@/api/room'

const rooms = ref([])
const dialogVisible = ref(false)
const currentRoom = ref({})
const isEdit = ref(false)
const roomTypes = ref([]) // 假设你有房间类型数据
const roomRules = {
  roomNumber: [{ required: true, message: '请输入房间号', trigger: 'blur' }],
  roomTypeId: [{ required: true, message: '请选择房间类型', trigger: 'change' }],
  floor: [{ required: true, message: '请输入楼层', trigger: 'blur' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
}

const fetchRooms = async () => {
  const response = await getRooms()
  rooms.value = response.data
}

const handleAdd = () => {
  currentRoom.value = { roomNumber: '', roomTypeId: null, floor: null, price: null, description: '' }
  isEdit.value = false
  dialogVisible.value = true // 打开对话框
}

const handleEdit = (row) => {
  currentRoom.value = { ...row }
  isEdit.value = true
  dialogVisible.value = true // 打开对话框
}

const handleSave = async () => {
  const formRef = ref(null)
  await formRef.value.validate(async (valid) => {
    if (valid) {
      if (isEdit.value) {
        await updateRoom(currentRoom.value.roomId, currentRoom.value)
      } else {
        await addRoom(currentRoom.value)
      }
      dialogVisible.value = false // 关闭对话框
      fetchRooms() // 刷新房间列表
    }
  })
}

const handleDelete = async (row) => {
  await deleteRoom(row.roomId)
  fetchRooms() // 刷新房间列表
}

// 状态类型和文本的获取函数
const getStatusType = (status) => {
  const statusMap = {
    0: 'success',
    1: 'warning',
    2: 'info',
    3: 'danger'
  }
  return statusMap[status]
}

const getStatusText = (status) => {
  const statusMap = {
    0: '空闲',
    1: '已预订',
    2: '已入住',
    3: '维护中'
  }
  return statusMap[status]
}

onMounted(() => {
  fetchRooms()
})
</script>

<style lang="scss" scoped>
.room-container {
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