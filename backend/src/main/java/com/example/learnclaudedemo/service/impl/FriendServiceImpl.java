package com.example.learnclaudedemo.service.impl;

import com.example.learnclaudedemo.entity.FriendRelation;
import com.example.learnclaudedemo.entity.User;
import com.example.learnclaudedemo.entity.UserAddress;
import com.example.learnclaudedemo.repository.FriendRelationRepository;
import com.example.learnclaudedemo.repository.UserAddressRepository;
import com.example.learnclaudedemo.repository.UserRepository;
import com.example.learnclaudedemo.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRelationRepository friendRepository;
    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;

    @Override
    public List<FriendRelation> getFriendList(Long userId) {
        return friendRepository.findAll().stream()
                .filter(f -> f.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> recommendFriends(Long userId) {
        User currentUser = userRepository.findById(userId).orElse(null);
        if (currentUser == null) {
            return List.of();
        }

        List<Long> friendIds = friendRepository.findAll().stream()
                .filter(f -> f.getUserId().equals(userId))
                .map(FriendRelation::getFriendId)
                .collect(Collectors.toList());

        friendIds.add(userId);

        return userRepository.findAll().stream()
                .filter(u -> !friendIds.contains(u.getId()))
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FriendRelation sendFriendRequest(Long userId, Long friendId, String remark) {
        FriendRelation existing = friendRepository.findAll().stream()
                .filter(f -> f.getUserId().equals(userId) && f.getFriendId().equals(friendId))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            return existing;
        }

        FriendRelation relation = new FriendRelation();
        relation.setUserId(userId);
        relation.setFriendId(friendId);
        relation.setRemark(remark);
        relation.setIsBlacklist(0);
        return friendRepository.save(relation);
    }

    @Override
    @Transactional
    public FriendRelation acceptFriendRequest(Long userId, Long friendId) {
        FriendRelation relation = friendRepository.findAll().stream()
                .filter(f -> f.getFriendId().equals(userId) && f.getUserId().equals(friendId))
                .findFirst()
                .orElse(null);

        if (relation != null) {
            return relation;
        }

        FriendRelation newRelation = new FriendRelation();
        newRelation.setUserId(userId);
        newRelation.setFriendId(friendId);
        newRelation.setIsBlacklist(0);
        return friendRepository.save(newRelation);
    }

    @Override
    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        friendRepository.findAll().stream()
                .filter(f -> f.getUserId().equals(userId) && f.getFriendId().equals(friendId))
                .findFirst()
                .ifPresent(relation -> friendRepository.delete(relation));
    }

    @Override
    public boolean areFriends(Long userId, Long friendId) {
        return friendRepository.findAll().stream()
                .anyMatch(f -> f.getUserId().equals(userId) && f.getFriendId().equals(friendId));
    }

    @Override
    public List<User> getNearbyUsers(Long userId, Double latitude, Double longitude, Integer radius) {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .filter(u -> !u.getId().equals(userId))
                .filter(u -> {
                    List<UserAddress> addresses = addressRepository.findAll().stream()
                            .filter(a -> a.getUserId().equals(u.getId()))
                            .collect(Collectors.toList());
                    return !addresses.isEmpty();
                })
                .limit(20)
                .collect(Collectors.toList());
    }
}
