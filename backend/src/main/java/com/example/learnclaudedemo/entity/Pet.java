package com.example.learnclaudedemo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Schema(description = "宠物实体信息")
@Table(name = "pet", indexes = {
        @Index(name = "idx_pet_type", columnList = "pet_type"),
        @Index(name = "idx_user_id", columnList = "user_id")
})
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "宠物ID", example = "1")
    private Long id;

    @Column(name = "user_id", nullable = false)
    @Schema(description = "用户ID", example = "1001")
    private Long userId;

    @Column(name = "pet_name", nullable = false, length = 50)
    @Schema(description = "宠物名称", example = "小白")
    private String petName;

    @Column(name = "pet_type", nullable = false, length = 20)
    @Schema(description = "宠物类型", example = "猫")
    private String petType;

    @Column(length = 50)
    @Schema(description = "宠物品种", example = "布偶")
    private String breed;

    @Schema(description = "性别 (0:未知, 1:公, 2:母)", example = "1")
    private Integer gender;

    @Column(name = "birth_date")
    @Schema(description = "出生日期")
    private LocalDate birthDate;

    @Schema(description = "体重 (kg)", example = "3.5")
    private BigDecimal weight;

    @Column(length = 500)
    @Schema(description = "头像URL")
    private String avatar;

    @Column(length = 255)
    @Schema(description = "宠物性格")
    private String personality;

    @Column(name = "health_status", length = 255)
    @Schema(description = "健康状态")
    private String healthStatus;

    @Schema(description = "是否绝育 (0:否, 1:是)", example = "1")
    private Integer sterilized;

    @Schema(description = "是否接种疫苗 (0:否, 1:是)", example = "1")
    private Integer vaccinated;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "详细描述")
    private String description;

    @Schema(description = "状态 (0:正常, 1:已领养, 2:已走丢)", example = "0")
    private Integer status;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}
