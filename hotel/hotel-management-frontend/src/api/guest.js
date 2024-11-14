import request from '@/utils/request'

export function getGuests() {
  return request({
    url: '/api/guests',
    method: 'get'
  })
}

export function addGuest(data) {
  return request({
    url: '/api/guests',
    method: 'post',
    data
  })
}

export function updateGuest(id, data) {
  return request({
    url: `/api/guests/${id}`,
    method: 'put',
    data
  })
}

export function deleteGuest(id) {
  return request({
    url: `/api/guests/${id}`,
    method: 'delete'
  })
} 