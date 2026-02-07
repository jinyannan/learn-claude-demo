package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message", indexes = {
    @Index(name = "idx_create_time", columnList = "create_time"),
    @Index(name = "idx_from_user", columnList = "from_user_id"),
    @Index(name = "idx_is_read", columnList = "is_read"),
    @Index(name = "idx_to_user", columnList = "to_user_id")
})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_user_id", nullable = false)
    private Long fromUserId;

    @Column(name = "to_user_id", nullable = false)
    private Long toUserId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "msg_type", length = 20)
    private String msgType;

    @Column(name = "is_read")
    private Integer isRead;

    @Column(name = "read_time")
    private LocalDateTime readTime;

    private Integer status;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

}
