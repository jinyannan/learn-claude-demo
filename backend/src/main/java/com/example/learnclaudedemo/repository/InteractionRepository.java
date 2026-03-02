package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    Interaction findByPostIdAndUserIdAndType(Long postId, Long userId, String type);

    boolean existsByPostIdAndUserIdAndType(Long postId, Long userId, String type);
}
