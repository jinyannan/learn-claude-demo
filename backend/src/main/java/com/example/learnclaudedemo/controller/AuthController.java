package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.config.Result;
import com.example.learnclaudedemo.dto.AuthDTO;
import com.example.learnclaudedemo.entity.User;
import com.example.learnclaudedemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "用户登录和注册相关接口")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<User> login(@RequestBody AuthDTO authDTO) {
        User user = userService.login(authDTO.getUsername(), authDTO.getPassword());
        if (user != null) {
            return Result.success(user);
        }
        return Result.error("用户名或密码错误");
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<User> register(@RequestBody AuthDTO authDTO) {
        User existingUser = userService.findByUsername(authDTO.getUsername());
        if (existingUser != null) {
            return Result.error("用户名已存在");
        }

        User user = new User();
        user.setUsername(authDTO.getUsername());
        user.setPassword(authDTO.getPassword());
        user.setEmail(authDTO.getEmail());
        user.setStatus(1);

        return Result.success(userService.create(user));
    }
}
