package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_address", indexes = {
    @Index(name = "idx_location", columnList = "location"),
    @Index(name = "idx_user_id", columnList = "user_id")
})
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "address_name", nullable = false, length = 50)
    private String addressName;

    @Column(length = 50)
    private String province;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String district;

    @Column(name = "detail_address", length = 255)
    private String detailAddress;

    @Column(nullable = false, columnDefinition = "GEOMETRY")
    private String location;

    @Column(name = "is_default")
    private Integer isDefault;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

}
