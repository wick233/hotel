package com.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.User;
import com.hotel.model.request.LoginRequest;
import com.hotel.model.response.LoginResponse;

public interface UserService extends IService<User> {
    LoginResponse login(LoginRequest loginRequest);
    User getUserByUsername(String username);
} 