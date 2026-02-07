package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> getChatList(Long userId);

    List<Message> getChatHistory(Long userId1, Long userId2);

    Message sendMessage(Long fromUserId, Long toUserId, String content, String msgType);

    void markAsRead(Long messageId);

    void markChatAsRead(Long fromUserId, Long toUserId);

    Long getUnreadCount(Long userId);

    void deleteMessage(Long messageId);
}
