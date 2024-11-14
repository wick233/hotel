import router from './index'
import { useUserStore } from '@/store/modules/user'

const whiteList = ['/login'] // 白名单

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const hasToken = localStorage.getItem('token')

  if (hasToken) {
    if (to.path === '/login') {
      next('/')
    } else {
      if (!Object.keys(userStore.userInfo).length) {
        try {
          await userStore.getUserInfo()
          next()
        } catch (error) {
          await userStore.logout()
          next('/login')
        }
      } else {
        next()
      }
    }
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }
  }
}) 