import request from '@/utils/request'

export function getRooms() {
  return request({
    url: '/api/rooms',
    method: 'get'
  })
}

export function addRoom(data) {
  return request({
    url: '/api/rooms',
    method: 'post',
    data
  })
}

export function updateRoom(id, data) {
  return request({
    url: `/api/rooms/${id}`,
    method: 'put',
    data
  })
}

export function deleteRoom(id) {
  return request({
    url: `/api/rooms/${id}`,
    method: 'delete'
  })
} 