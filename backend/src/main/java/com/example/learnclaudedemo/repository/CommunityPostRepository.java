package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

    List<CommunityPost> findByUserId(Long userId);

    List<CommunityPost> findByPetId(Long petId);

    List<CommunityPost> findByPostType(String postType);

    List<CommunityPost> findByStatus(Integer status);

}
