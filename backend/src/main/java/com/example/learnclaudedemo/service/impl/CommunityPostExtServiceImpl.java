package com.example.learnclaudedemo.service.impl;

import com.example.learnclaudedemo.dto.FeedPostDto;
import com.example.learnclaudedemo.dto.request.PostPublishRequest;
import com.example.learnclaudedemo.entity.CommunityPost;
import com.example.learnclaudedemo.entity.Interaction;
import com.example.learnclaudedemo.repository.CommunityPostRepository;
import com.example.learnclaudedemo.repository.InteractionRepository;
import com.example.learnclaudedemo.service.CommunityPostExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityPostExtServiceImpl implements CommunityPostExtService {

    @Autowired
    private CommunityPostRepository postRepository;

    @Autowired
    private InteractionRepository interactionRepository;

    @Override
    public List<FeedPostDto> getDiscoveryFeed(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        // 实际中可能需要结合推荐算法，这里简化为按时间倒序
        Page<CommunityPost> postPage = postRepository.findAll(pageable);

        return postPage.getContent().stream().map(post -> {
            FeedPostDto dto = new FeedPostDto();
            dto.setId(post.getId());
            dto.setUserId(post.getUserId());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setTags(post.getTags());

            // 解析图片集合
            if (post.getImages() != null && !post.getImages().isEmpty()) {
                dto.setImages(Arrays.asList(post.getImages().split(",")));
            } else {
                dto.setImages(new ArrayList<>());
            }

            // 组装统计数据
            dto.setLikeCount(post.getLikeCount() != null ? post.getLikeCount() : 0);
            dto.setCommentCount(post.getCommentCount() != null ? post.getCommentCount() : 0);
            dto.setViewCount(post.getViewCount() != null ? post.getViewCount() : 0);
            dto.setCreateTime(post.getCreateTime());

            // TODO: 调用 User/Pet Service 获取名字和头像
            dto.setUserName("User " + post.getUserId());
            dto.setUserAvatar("https://picsum.photos/100/100?random=" + post.getUserId());

            // 检查当前用户是否点赞
            if (userId != null) {
                dto.setIsLiked(interactionRepository.existsByPostIdAndUserIdAndType(post.getId(), userId, "LIKE"));
                // 暂时 mock isFollowed
                dto.setIsFollowed(false);
            } else {
                dto.setIsLiked(false);
                dto.setIsFollowed(false);
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void publishPost(Long userId, PostPublishRequest request) {
        String insertSql = "INSERT INTO community_post " +
                "(user_id, pet_id, title, content, tags, location, images, like_count, comment_count, view_count, status, create_time, update_time) "
                +
                "VALUES (?, ?, ?, ?, ?, ST_GeomFromText(?), ?, 0, 0, 0, 1, NOW(), NOW())";

        String pointStr = "POINT(0 0)";
        if (request.getLongitude() != null && request.getLatitude() != null) {
            pointStr = "POINT(" + request.getLongitude() + " " + request.getLatitude() + ")";
        }

        String imagesStr = null;
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            imagesStr = String.join(",", request.getImages());
        }

        jdbcTemplate.update(insertSql,
                userId,
                request.getPetId(),
                request.getTitle(),
                request.getContent(),
                request.getTags(),
                pointStr,
                imagesStr);
    }

    @Override
    @Transactional
    public void toggleLike(Long userId, Long postId) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Interaction existLike = interactionRepository.findByPostIdAndUserIdAndType(postId, userId, "LIKE");

        if (existLike != null) {
            // 已经点过赞，取消赞
            interactionRepository.delete(existLike);
            post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
        } else {
            // 新增赞
            Interaction newLike = new Interaction();
            newLike.setPostId(postId);
            newLike.setUserId(userId);
            newLike.setType("LIKE");
            interactionRepository.save(newLike);

            post.setLikeCount((post.getLikeCount() == null ? 0 : post.getLikeCount()) + 1);
        }

        postRepository.save(post);
    }
}
