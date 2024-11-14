import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/guests',
    name: 'GuestManagement',
    component: () => import('@/views/guest/index.vue'),
    meta: { title: '客户管理', icon: 'guest' }
  },
  {
    path: '/reservations',
    name: 'ReservationManagement',
    component: () => import('@/views/reservation/index.vue'),
    meta: { title: '预订管理', icon: 'reservation' }
  },
  {
    path: '/rooms',
    name: 'RoomManagement',
    component: () => import('@/views/room/index.vue'),
    meta: { title: '房间管理', icon: 'room' }
  },
  {
    path: '/',
    component: () => import('@/components/layout/index.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '首页', icon: 'dashboard' }
      },
      {
        path: 'room',
        name: 'Room',
        component: () => import('@/views/room/index.vue'),
        meta: { title: '房间管理', icon: 'room' }
      },
      {
        path: 'reservation',
        name: 'Reservation',
        component: () => import('@/views/reservation/index.vue'),
        meta: { title: '预订管理', icon: 'reservation' }
      },
      {
        path: 'guest',
        name: 'Guest',
        component: () => import('@/views/guest/index.vue'),
        meta: { title: '客户管理', icon: 'guest' }
      },
      {
        path: 'system',
        name: 'System',
        meta: { title: '系管理', icon: 'setting' },
        children: [
          {
            path: 'user',
            name: 'User',
            component: () => import('@/views/system/user/index.vue'),
            meta: { title: '用户管理' }
          },
          {
            path: 'role',
            name: 'Role',
            component: () => import('@/views/system/role/index.vue'),
            meta: { title: '角色管理' }
          }
        ]
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 