<template>
  <el-container>
    <el-aside width="200px">
      <Sidebar />
    </el-aside>
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import Sidebar from './Sidebar.vue'
import Header from './Header.vue'
</script>

<style lang="scss" scoped>
.el-header {
  border-bottom: 1px solid #e6e6e6;
  background-color: #fff;
}

.el-aside {
  background-color: #304156;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style> 