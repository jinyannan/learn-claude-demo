package com.example.learnclaudedemo.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedPostDto {
    private Long id;

    // 发布者信息
    private Long userId;
    private String userName;
    private String userAvatar;

    // 宠物信息 (如果有)
    private Long petId;
    private String petName;
    private String petAvatar;

    // 动态内容
    private String title;
    private String content;
    private List<String> images; // 将逗号分隔的字符串转为列表返回
    private String tags;

    // 互动数据
    private Integer likeCount;
    private Integer commentCount;
    private Integer viewCount;

    // 当前用户维度的交互状态
    private Boolean isLiked; // 当前用户是否已点赞
    private Boolean isFollowed; // 当前用户是否已关注发布者

    // 时间
    private LocalDateTime createTime;
}
