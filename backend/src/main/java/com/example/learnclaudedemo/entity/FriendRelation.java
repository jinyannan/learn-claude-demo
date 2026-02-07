package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "friend_relation", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_friend", columnNames = {"user_id", "friend_id"})
}, indexes = {
    @Index(name = "idx_friend_id", columnList = "friend_id")
})
public class FriendRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "friend_id", nullable = false)
    private Long friendId;

    @Column(length = 50)
    private String remark;

    @Column(name = "is_blacklist")
    private Integer isBlacklist;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

}
