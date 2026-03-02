package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.dto.FeedPostDto;
import com.example.learnclaudedemo.dto.request.PostPublishRequest;

import java.util.List;

public interface CommunityPostExtService {
    
    /**
     * 获取发现大厅Feed流
     * @param userId 当前用户ID (如果已登录，用于判断点赞状态)
     * @param page 页码
     * @param size 每页数量
     */
    List<FeedPostDto> getDiscoveryFeed(Long userId, int page, int size);
    
    /**
     * 发布新动态
     */
    void publishPost(Long userId, PostPublishRequest request);
    
    /**
     * 点赞 / 取消点赞
     */
    void toggleLike(Long userId, Long postId);
}
