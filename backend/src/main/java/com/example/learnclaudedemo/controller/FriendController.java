package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.entity.FriendRelation;
import com.example.learnclaudedemo.entity.User;
import com.example.learnclaudedemo.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@Tag(name = "好友管理", description = "好友关系管理相关接口")
public class FriendController {

    private final FriendService friendService;

    @GetMapping
    @Operation(summary = "获取好友列表")
    public List<FriendRelation> getFriendList(
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return friendService.getFriendList(userId);
    }

    @GetMapping("/recommend")
    @Operation(summary = "推荐好友")
    public List<User> recommendFriends(
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return friendService.recommendFriends(userId);
    }

    @PostMapping("/request")
    @Operation(summary = "发送好友请求")
    public FriendRelation sendRequest(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "目标好友ID") @RequestParam Long friendId,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return friendService.sendFriendRequest(userId, friendId, remark);
    }

    @PutMapping("/{friendId}/accept")
    @Operation(summary = "接受好友请求")
    public FriendRelation acceptRequest(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @PathVariable Long friendId) {
        return friendService.acceptFriendRequest(userId, friendId);
    }

    @DeleteMapping("/{friendId}")
    @Operation(summary = "删除好友")
    public void deleteFriend(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @PathVariable Long friendId) {
        friendService.deleteFriend(userId, friendId);
    }

    @GetMapping("/check")
    @Operation(summary = "检查是否为好友")
    public boolean checkFriendship(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "目标用户ID") @RequestParam Long friendId) {
        return friendService.areFriends(userId, friendId);
    }

    @GetMapping("/nearby")
    @Operation(summary = "获取附近用户")
    public List<User> getNearbyUsers(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "纬度") @RequestParam Double latitude,
            @Parameter(description = "经度") @RequestParam Double longitude,
            @Parameter(description = "半径(km)") @RequestParam(defaultValue = "10") Integer radius) {
        return friendService.getNearbyUsers(userId, latitude, longitude, radius);
    }
}
