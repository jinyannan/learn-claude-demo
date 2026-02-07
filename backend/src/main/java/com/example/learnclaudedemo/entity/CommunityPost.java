package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "community_post", indexes = {
    @Index(name = "idx_create_time", columnList = "create_time"),
    @Index(name = "idx_location", columnList = "location"),
    @Index(name = "idx_pet_id", columnList = "pet_id"),
    @Index(name = "idx_post_type", columnList = "post_type"),
    @Index(name = "idx_user_id", columnList = "user_id")
})
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "pet_id")
    private Long petId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String images;

    @Column(name = "post_type", length = 20)
    private String postType;

    @Column(length = 255)
    private String tags;

    @Column(nullable = false, columnDefinition = "GEOMETRY")
    private String location;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "is_top")
    private Integer isTop;

    private Integer status;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

}
