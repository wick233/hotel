package com.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.User;
import com.hotel.mapper.UserMapper;
import com.hotel.model.request.LoginRequest;
import com.hotel.model.response.LoginResponse;
import com.hotel.service.UserService;
import com.hotel.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            System.out.println("开始登录，用户名：" + loginRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            System.out.println("认证成功");

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            String token = jwtUtils.generateToken(userDetails);
            
            User user = getUserByUsername(loginRequest.getUsername());
            System.out.println("获取到用户信息：" + user);
            
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            
            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            userInfo.setName(user.getName());
            userInfo.setEmail(user.getEmail());
            userInfo.setPhone(user.getPhone());
            
            response.setUserInfo(userInfo);
            return response;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new RuntimeException("用户名或密码错误: " + e.getMessage());
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getDeleted, 0));
    }
} 