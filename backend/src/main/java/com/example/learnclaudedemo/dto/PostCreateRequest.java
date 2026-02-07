package com.example.learnclaudedemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "创建/更新帖子请求")
public class PostCreateRequest {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "宠物ID")
    private Long petId;

    @Schema(description = "标题", required = true)
    private String title;

    @Schema(description = "内容", required = true)
    private String content;

    @Schema(description = "图片URLs（逗号分隔）")
    private String images;

    @Schema(description = "帖子类型（daily/qa/lost/found）")
    private String postType;

    @Schema(description = "标签（逗号分隔）")
    private String tags;

    @Schema(description = "位置（经纬度）")
    private String location;
}
