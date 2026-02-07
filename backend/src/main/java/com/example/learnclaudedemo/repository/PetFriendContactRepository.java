package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.PetFriendContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetFriendContactRepository extends JpaRepository<PetFriendContact, Long> {

    List<PetFriendContact> findByUserIdOrderByCreateTimeDesc(Long userId);

    Optional<PetFriendContact> findByUserIdAndContactUserId(Long userId, Long contactUserId);

}
