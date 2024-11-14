package com.hotel.service;

import com.hotel.entity.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();
    Reservation addReservation(Reservation reservation);
    Reservation updateReservation(Integer id, Reservation reservation);
    void deleteReservation(Integer id);
} 