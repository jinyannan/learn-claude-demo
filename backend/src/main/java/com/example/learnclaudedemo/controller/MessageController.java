package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.entity.Message;
import com.example.learnclaudedemo.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "消息管理", description = "即时消息相关接口")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/chats")
    @Operation(summary = "获取聊天列表")
    public List<Message> getChatList(
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return messageService.getChatList(userId);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "获取与特定用户的聊天记录")
    public List<Message> getChatHistory(
            @Parameter(description = "当前用户ID") @RequestParam Long currentUserId,
            @PathVariable Long userId) {
        return messageService.getChatHistory(currentUserId, userId);
    }

    @PostMapping
    @Operation(summary = "发送消息")
    public Message sendMessage(
            @Parameter(description = "发送者ID") @RequestParam Long fromUserId,
            @Parameter(description = "接收者ID") @RequestParam Long toUserId,
            @Parameter(description = "消息内容") @RequestParam String content,
            @Parameter(description = "消息类型") @RequestParam(defaultValue = "text") String msgType) {
        return messageService.sendMessage(fromUserId, toUserId, content, msgType);
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "标记消息已读")
    public void markAsRead(@PathVariable Long id) {
        messageService.markAsRead(id);
    }

    @PutMapping("/chat/read")
    @Operation(summary = "标记与某用户的聊天已读")
    public void markChatAsRead(
            @Parameter(description = "发送者ID") @RequestParam Long fromUserId,
            @Parameter(description = "接收者ID") @RequestParam Long toUserId) {
        messageService.markChatAsRead(fromUserId, toUserId);
    }

    @GetMapping("/unread")
    @Operation(summary = "获取未读消息数量")
    public Long getUnreadCount(
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return messageService.getUnreadCount(userId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除消息")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }
}
