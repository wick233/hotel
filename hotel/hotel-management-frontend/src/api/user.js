import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/auth/login',
    method: 'post',
    data
  }).then(response => {
    // 假设返回的响应中包含token
    localStorage.setItem('token', response.data.token);
    return response;
  });
}

export function getUserInfo() {
  return request({
    url: '/api/auth/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api/auth/logout',
    method: 'post'
  }).then(() => {
    localStorage.removeItem('token'); // 清除本地存储的token
  });
} 