import { defineStore } from 'pinia'
import { login, getUserInfo, logout } from '@/api/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: {}
  }),
  
  actions: {
    async login(loginForm) {
      try {
        const { data } = await login(loginForm)
        this.token = data.token
        localStorage.setItem('token', data.token)
        await this.getUserInfo()
        return data
      } catch (error) {
        throw error
      }
    },
    
    async getUserInfo() {
      try {
        const { data } = await getUserInfo()
        this.userInfo = data
        return data
      } catch (error) {
        throw error
      }
    },
    
    async logout() {
      try {
        await logout()
        this.token = ''
        this.userInfo = {}
        localStorage.removeItem('token')
      } catch (error) {
        throw error
      }
    }
  }
}) 