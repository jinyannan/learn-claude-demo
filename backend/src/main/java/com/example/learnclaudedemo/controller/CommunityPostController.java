package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.dto.CommentCreateRequest;
import com.example.learnclaudedemo.dto.PostCreateRequest;
import com.example.learnclaudedemo.entity.CommunityPost;
import com.example.learnclaudedemo.entity.PostComment;
import com.example.learnclaudedemo.service.CommunityPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "社区帖子", description = "社区帖子管理相关接口")
public class CommunityPostController {

    private final CommunityPostService postService;

    @GetMapping
    @Operation(summary = "获取帖子列表")
    public List<CommunityPost> list(
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "20") Integer size) {
        return postService.findPublishedPosts(page, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取帖子详情")
    public CommunityPost getById(@PathVariable Long id) {
        postService.incrementViewCount(id);
        return postService.findById(id);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户的帖子列表")
    public List<CommunityPost> getByUserId(@PathVariable Long userId) {
        return postService.findByUserId(userId);
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "获取宠物的帖子列表")
    public List<CommunityPost> getByPetId(@PathVariable Long petId) {
        return postService.findByPetId(petId);
    }

    @GetMapping("/type/{postType}")
    @Operation(summary = "按类型获取帖子")
    public List<CommunityPost> getByType(@PathVariable String postType) {
        return postService.findByPostType(postType);
    }

    @PostMapping
    @Operation(summary = "创建帖子")
    public CommunityPost create(@RequestBody PostCreateRequest request) {
        return postService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新帖子")
    public CommunityPost update(@PathVariable Long id, @RequestBody PostCreateRequest request) {
        return postService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除帖子")
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "点赞帖子")
    public void like(@PathVariable Long id,
                     @Parameter(description = "用户ID") @RequestParam Long userId) {
        postService.toggleLike(id, userId);
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "获取帖子评论列表")
    public List<PostComment> getComments(@PathVariable Long id) {
        return postService.getComments(id);
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "发表评论")
    public PostComment addComment(@PathVariable Long id, @RequestBody CommentCreateRequest request) {
        request.setPostId(id);
        return postService.addComment(request);
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "删除评论")
    public void deleteComment(@PathVariable Long commentId) {
        postService.deleteComment(commentId);
    }
}
