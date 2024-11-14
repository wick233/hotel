package com.hotel.controller;

import com.hotel.common.Result;
import com.hotel.entity.User;
import com.hotel.model.request.LoginRequest;
import com.hotel.model.response.LoginResponse;
import com.hotel.model.response.UserInfoResponse;
import com.hotel.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return Result.success(userService.login(loginRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result<UserInfoResponse> getUserInfo(Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUsername(user.getUsername());
        userInfoResponse.setName(user.getName());
        userInfoResponse.setEmail(user.getEmail());
        userInfoResponse.setPhone(user.getPhone());
        
        return Result.success(userInfoResponse);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success(null);
    }
} 