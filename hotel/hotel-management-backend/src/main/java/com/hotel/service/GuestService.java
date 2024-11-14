package com.hotel.service;

import com.hotel.entity.Guest;

import java.util.List;

public interface GuestService {
    List<Guest> getAllGuests();
    Guest addGuest(Guest guest);
    Guest updateGuest(Integer id, Guest guest);
    void deleteGuest(Integer id);
} 