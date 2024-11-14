package com.hotel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.Guest;
import com.hotel.mapper.GuestMapper;
import com.hotel.service.GuestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl extends ServiceImpl<GuestMapper, Guest> implements GuestService {

    private final GuestMapper guestMapper;

    public GuestServiceImpl(GuestMapper guestMapper) {
        this.guestMapper = guestMapper;
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestMapper.selectList(null);
    }

    @Override
    public Guest addGuest(Guest guest) {
        guestMapper.insert(guest);
        return guest;
    }

    @Override
    public Guest updateGuest(Integer id, Guest guest) {
        guest.setGuestId(id);
        guestMapper.updateById(guest);
        return guest;
    }

    @Override
    public void deleteGuest(Integer id) {
        guestMapper.deleteById(id);
    }
} 