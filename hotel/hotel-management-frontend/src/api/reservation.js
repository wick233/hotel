import request from '@/utils/request'

export function getReservations() {
  return request({
    url: '/api/reservations',
    method: 'get'
  })
}

export function addReservation(data) {
  return request({
    url: '/api/reservations',
    method: 'post',
    data
  })
}

export function updateReservation(id, data) {
  return request({
    url: `/api/reservations/${id}`,
    method: 'put',
    data
  })
}

export function deleteReservation(id) {
  return request({
    url: `/api/reservations/${id}`,
    method: 'delete'
  })
} 