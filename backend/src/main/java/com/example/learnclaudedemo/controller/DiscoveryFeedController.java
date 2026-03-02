package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.dto.FeedPostDto;
import com.example.learnclaudedemo.dto.request.PostPublishRequest;
import com.example.learnclaudedemo.service.CommunityPostExtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
public class DiscoveryFeedController {

    @Autowired
    private CommunityPostExtService postExtService;

    /**
     * 获取发现大厅Feed流
     * 假设前端会在 Headers 传入 userId，实际开发中应该从 Token Context获取
     */
    @GetMapping
    public ResponseEntity<List<FeedPostDto>> getDiscoveryFeed(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<FeedPostDto> feed = postExtService.getDiscoveryFeed(userId, page, size);
        return ResponseEntity.ok(feed);
    }

    /**
     * 发布新动态
     */
    @PostMapping
    public ResponseEntity<String> publishPost(
            @RequestHeader(value = "X-User-Id") Long userId,
            @Valid @RequestBody PostPublishRequest request) {

        postExtService.publishPost(userId, request);
        return ResponseEntity.ok("success");
    }

    /**
     * 点赞/取消点赞
     */
    @PostMapping("/{postId}/like")
    public ResponseEntity<String> toggleLike(
            @RequestHeader(value = "X-User-Id") Long userId,
            @PathVariable Long postId) {

        postExtService.toggleLike(userId, postId);
        return ResponseEntity.ok("success");
    }
}
