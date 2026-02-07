package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pet_friend_contact", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_contact", columnNames = {"user_id", "contact_user_id"})
}, indexes = {
    @Index(name = "idx_contact_user", columnList = "contact_user_id")
})
public class PetFriendContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "contact_user_id", nullable = false)
    private Long contactUserId;

    @Column(name = "contact_name", length = 50)
    private String contactName;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "contact_wechat", length = 50)
    private String contactWechat;

    @Column(name = "contact_qq", length = 20)
    private String contactQq;

    @Column(length = 255)
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

}
