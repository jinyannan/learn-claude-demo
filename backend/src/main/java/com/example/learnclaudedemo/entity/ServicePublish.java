package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service_publish", indexes = {
    @Index(name = "idx_location", columnList = "address_id"),
    @Index(name = "idx_service_type", columnList = "service_type_id"),
    @Index(name = "idx_user_id", columnList = "user_id")
})
public class ServicePublish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "service_type_id", nullable = false)
    private Long serviceTypeId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "address_id", nullable = false)
    private Long addressId;

    @Column(name = "service_radius")
    private Integer serviceRadius;

    private BigDecimal price;

    @Column(name = "price_unit", length = 20)
    private String priceUnit;

    @Column(name = "available_time", length = 255)
    private String availableTime;

    @Column(name = "pet_types", length = 255)
    private String petTypes;

    private Integer status;

    @Column(name = "view_count")
    private Integer viewCount;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

}
