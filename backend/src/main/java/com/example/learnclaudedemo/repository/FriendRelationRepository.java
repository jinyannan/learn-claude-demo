package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.FriendRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {

    List<FriendRelation> findByUserId(Long userId);

    Optional<FriendRelation> findByUserIdAndFriendId(Long userId, Long friendId);

    List<FriendRelation> findByUserIdAndIsBlacklist(Long userId, Integer isBlacklist);

}
