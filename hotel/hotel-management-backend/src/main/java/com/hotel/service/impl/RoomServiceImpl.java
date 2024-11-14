package com.hotel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.Room;
import com.hotel.mapper.RoomMapper;
import com.hotel.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomMapper.selectList(null);
    }

    @Override
    public Room addRoom(Room room) {
        roomMapper.insert(room);
        return room;
    }

    @Override
    public Room updateRoom(Integer id, Room room) {
        room.setRoomId(id);
        roomMapper.updateById(room);
        return room;
    }

    @Override
    public void deleteRoom(Integer id) {
        roomMapper.deleteById(id);
    }
} 