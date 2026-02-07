package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.entity.FriendRelation;
import com.example.learnclaudedemo.entity.User;

import java.util.List;

public interface FriendService {

    List<FriendRelation> getFriendList(Long userId);

    List<User> recommendFriends(Long userId);

    FriendRelation sendFriendRequest(Long userId, Long friendId, String remark);

    FriendRelation acceptFriendRequest(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    boolean areFriends(Long userId, Long friendId);

    List<User> getNearbyUsers(Long userId, Double latitude, Double longitude, Integer radius);
}
