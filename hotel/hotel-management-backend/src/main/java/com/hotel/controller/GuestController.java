package com.hotel.controller;

import com.hotel.common.Result;
import com.hotel.entity.Guest;
import com.hotel.service.GuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public Result<List<Guest>> getAllGuests() {
        List<Guest> guests = guestService.getAllGuests();
        return Result.success(guests);
    }

    @PostMapping
    public Result<Guest> addGuest(@RequestBody Guest guest) {
        Guest savedGuest = guestService.addGuest(guest);
        return Result.success(savedGuest);
    }

    @PutMapping("/{id}")
    public Result<Guest> updateGuest(@PathVariable Integer id, @RequestBody Guest guest) {
        Guest updatedGuest = guestService.updateGuest(id, guest);
        return Result.success(updatedGuest);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteGuest(@PathVariable Integer id) {
        guestService.deleteGuest(id);
        return Result.success(null);
    }
} 