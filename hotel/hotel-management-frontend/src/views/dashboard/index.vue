<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>今日入住</span>
            </div>
          </template>
          <div class="card-body">
            <h2>{{ statistics.checkInToday }}</h2>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>今日预订</span>
            </div>
          </template>
          <div class="card-body">
            <h2>{{ statistics.reservationToday }}</h2>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>空闲房间</span>
            </div>
          </template>
          <div class="card-body">
            <h2>{{ statistics.availableRooms }}</h2>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>今日收入</span>
            </div>
          </template>
          <div class="card-body">
            <h2>¥{{ statistics.incomeToday }}</h2>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

// 模拟数据，实际应该从API获取
const statistics = reactive({
  checkInToday: 12,
  reservationToday: 25,
  availableRooms: 45,
  incomeToday: '12,580'
})
</script>

<style lang="scss" scoped>
.dashboard-container {
  .el-card {
    margin-bottom: 20px;
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    
    .card-body {
      text-align: center;
      h2 {
        margin: 0;
        font-size: 28px;
        color: #303133;
      }
    }
  }
}
</style> 