package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service_order", uniqueConstraints = {
    @UniqueConstraint(name = "uk_order_no", columnNames = "order_no")
}, indexes = {
    @Index(name = "idx_customer", columnList = "customer_id"),
    @Index(name = "idx_provider", columnList = "provider_id"),
    @Index(name = "idx_status", columnList = "status")
})
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true, length = 32)
    private String orderNo;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "service_time")
    private LocalDateTime serviceTime;

    @Column(name = "service_address", length = 255)
    private String serviceAddress;

    private BigDecimal price;

    @Column(length = 20)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

}
