import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const service = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1])) // 解码JWT
      const exp = payload.exp * 1000 // 转换为毫秒
      const now = Date.now()

      // 检查JWT是否过期
      if (now >= exp) {
        localStorage.removeItem('token') // 清除过期的token
        ElMessage.error('您的登录已过期，请重新登录')
        useRouter().push('/login') // 跳转到登录页面
        return Promise.reject(new Error('Token expired'))
      }

      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  error => {
    console.error('响应错误:', error)
    if (error.response) {
      switch (error.response.status) {
        case 401:
          ElMessage.error('用户名或密码错误')
          break
        case 403:
          ElMessage.error('没有权限访问')
          break
        default:
          ElMessage.error(error.response.data?.message || '请求失败')
      }
    } else {
      ElMessage.error('网络错误，请稍后重试')
    }
    return Promise.reject(error)
  }
)

export default service 