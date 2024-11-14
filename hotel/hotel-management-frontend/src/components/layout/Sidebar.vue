<template>
  <div class="sidebar-container">
    <div class="logo">
      <span>酒店管理系统</span>
    </div>
    <el-menu
      :default-active="activeMenu"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
      :unique-opened="true"
      :collapse-transition="false"
      mode="vertical"
      router
    >
      <template v-for="route in routes" :key="route.path">
        <!-- 没有子菜单的路由 -->
        <el-menu-item v-if="!route.children" :index="route.path">
          <el-icon><component :is="route.meta?.icon" /></el-icon>
          <template #title>{{ route.meta?.title }}</template>
        </el-menu-item>

        <!-- 有子菜单的路由 -->
        <el-sub-menu v-else :index="route.path">
          <template #title>
            <el-icon><component :is="route.meta?.icon" /></el-icon>
            <span>{{ route.meta?.title }}</span>
          </template>
          <el-menu-item 
            v-for="child in route.children"
            :key="child.path"
            :index="`${route.path}/${child.path}`"
          >
            {{ child.meta?.title }}
          </el-menu-item>
        </el-sub-menu>
      </template>
    </el-menu>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { House, Box, Calendar, User, Setting } from '@element-plus/icons-vue'

const route = useRoute()

const activeMenu = computed(() => route.path)

// 菜单配置
const routes = [
  {
    path: '/dashboard',
    meta: { title: '首页', icon: 'House' }
  },
  {
    path: '/room',
    meta: { title: '房间管理', icon: 'Box' }
  },
  {
    path: '/reservation',
    meta: { title: '预订管理', icon: 'Calendar' }
  },
  {
    path: '/guest',
    meta: { title: '客户管理', icon: 'User' }
  },
  {
    path: '/system',
    meta: { title: '系统管理', icon: 'Setting' },
    children: [
      {
        path: 'user',
        meta: { title: '用户管理' }
      },
      {
        path: 'role',
        meta: { title: '角色管理' }
      }
    ]
  }
]
</script>

<style lang="scss" scoped>
.sidebar-container {
  height: 100%;
  .logo {
    height: 60px;
    line-height: 60px;
    text-align: center;
    color: #fff;
    font-size: 18px;
    font-weight: bold;
  }
  .el-menu {
    border-right: none;
  }
}
</style> 