package com.example.learnclaudedemo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class PostPublishRequest {

    // 宠物ID (可选)
    private Long petId;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    // 高级UI下多图上传是核心
    private List<String> images;

    // 获取的当前地理位置
    private String latitude;
    private String longitude;

    private String tags;
}
