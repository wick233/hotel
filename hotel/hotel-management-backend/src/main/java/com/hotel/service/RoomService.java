package com.hotel.service;

import com.hotel.entity.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    Room addRoom(Room room);
    Room updateRoom(Integer id, Room room);
    void deleteRoom(Integer id);
} 