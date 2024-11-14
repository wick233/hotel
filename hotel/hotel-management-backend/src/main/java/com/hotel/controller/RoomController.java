package com.hotel.controller;

import com.hotel.common.Result;
import com.hotel.entity.Room;
import com.hotel.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public Result<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return Result.success(rooms);
    }

    @PostMapping
    public Result<Room> addRoom(@RequestBody Room room) {
        Room savedRoom = roomService.addRoom(room);
        return Result.success(savedRoom);
    }

    @PutMapping("/{id}")
    public Result<Room> updateRoom(@PathVariable Integer id, @RequestBody Room room) {
        Room updatedRoom = roomService.updateRoom(id, room);
        return Result.success(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
        return Result.success(null);
    }
} 