package com.example.learnclaudedemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "附近搜索请求")
public class NearbySearchRequest {

    @Schema(description = "纬度", required = true)
    private Double latitude;

    @Schema(description = "经度", required = true)
    private Double longitude;

    @Schema(description = "搜索半径(km)", defaultValue = "10")
    private Integer radius = 10;

    @Schema(description = "当前用户ID")
    private Long userId;

    @Schema(description = "宠物类型筛选")
    private String petType;

    @Schema(description = "服务类型ID")
    private Long serviceTypeId;
}
