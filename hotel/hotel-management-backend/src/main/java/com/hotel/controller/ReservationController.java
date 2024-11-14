package com.hotel.controller;

import com.hotel.common.Result;
import com.hotel.entity.Reservation;
import com.hotel.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public Result<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return Result.success(reservations);
    }

    @PostMapping
    public Result<Reservation> addReservation(@RequestBody Reservation reservation) {
        Reservation savedReservation = reservationService.addReservation(reservation);
        return Result.success(savedReservation);
    }

    @PutMapping("/{id}")
    public Result<Reservation> updateReservation(@PathVariable Integer id, @RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservation);
        return Result.success(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
        return Result.success(null);
    }
} 