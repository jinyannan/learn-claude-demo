package com.example.learnclaudedemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "创建服务订单请求")
public class ServiceOrderRequest {

    @Schema(description = "服务ID", required = true)
    private Long serviceId;

    @Schema(description = "服务提供者ID", required = true)
    private Long providerId;

    @Schema(description = "客户ID", required = true)
    private Long customerId;

    @Schema(description = "宠物ID", required = true)
    private Long petId;

    @Schema(description = "服务时间")
    private LocalDateTime serviceTime;

    @Schema(description = "服务地址")
    private String serviceAddress;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "备注")
    private String remark;
}
