package com.hotel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.Reservation;
import com.hotel.mapper.ReservationMapper;
import com.hotel.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {

    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationMapper.selectList(null);
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        reservationMapper.insert(reservation);
        return reservation;
    }

    @Override
    public Reservation updateReservation(Integer id, Reservation reservation) {
        reservation.setReservationId(id);
        reservationMapper.updateById(reservation);
        return reservation;
    }

    @Override
    public void deleteReservation(Integer id) {
        reservationMapper.deleteById(id);
    }
} 