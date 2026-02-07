package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.dto.CommentCreateRequest;
import com.example.learnclaudedemo.dto.PostCreateRequest;
import com.example.learnclaudedemo.entity.CommunityPost;
import com.example.learnclaudedemo.entity.PostComment;

import java.util.List;

public interface CommunityPostService {

    List<CommunityPost> findAll();

    CommunityPost findById(Long id);

    List<CommunityPost> findByUserId(Long userId);

    List<CommunityPost> findByPetId(Long petId);

    List<CommunityPost> findByPostType(String postType);

    List<CommunityPost> findPublishedPosts(Integer page, Integer size);

    CommunityPost create(PostCreateRequest request);

    CommunityPost update(Long id, PostCreateRequest request);

    void delete(Long id);

    void toggleLike(Long postId, Long userId);

    List<PostComment> getComments(Long postId);

    PostComment addComment(CommentCreateRequest request);

    void deleteComment(Long commentId);

    void incrementViewCount(Long postId);
}
