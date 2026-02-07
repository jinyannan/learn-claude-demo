package com.example.learnclaudedemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "创建评论请求")
public class CommentCreateRequest {

    @Schema(description = "帖子ID", required = true)
    private Long postId;

    @Schema(description = "用户ID", required = true)
    private Long userId;

    @Schema(description = "评论内容", required = true)
    private String content;

    @Schema(description = "父评论ID（回复时使用）")
    private Long parentId;

    @Schema(description = "被回复用户ID（回复时使用）")
    private Long replyToUserId;
}
