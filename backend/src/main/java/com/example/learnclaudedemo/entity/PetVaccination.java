package com.example.learnclaudedemo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Schema(description = "宠物疫苗接种记录")
@Table(name = "pet_vaccination", indexes = {
        @Index(name = "idx_pet_id", columnList = "pet_id")
})
public class PetVaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "记录ID", example = "1")
    private Long id;

    @Column(name = "pet_id", nullable = false)
    @Schema(description = "宠物ID", example = "1")
    private Long petId;

    @Column(name = "vaccine_name", nullable = false, length = 100)
    @Schema(description = "疫苗名称", example = "狂犬病疫苗")
    private String vaccineName;

    @Column(name = "vaccination_date", nullable = false)
    @Schema(description = "接种日期")
    private LocalDate vaccinationDate;

    @Column(name = "next_dose_date")
    @Schema(description = "下次接种日期")
    private LocalDate nextDoseDate;

    @Column(length = 100)
    @Schema(description = "接种机构", example = "爱宠医院")
    private String provider;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "备注")
    private String remarks;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
