package com.example.learnclaudedemo.service.impl;

import com.example.learnclaudedemo.entity.Message;
import com.example.learnclaudedemo.repository.MessageRepository;
import com.example.learnclaudedemo.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public List<Message> getChatList(Long userId) {
        List<Message> allMessages = messageRepository.findAll();

        return allMessages.stream()
                .filter(m -> m.getFromUserId().equals(userId) || m.getToUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getChatHistory(Long userId1, Long userId2) {
        return messageRepository.findAll().stream()
                .filter(m -> (m.getFromUserId().equals(userId1) && m.getToUserId().equals(userId2)) ||
                        (m.getFromUserId().equals(userId2) && m.getToUserId().equals(userId1)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Message sendMessage(Long fromUserId, Long toUserId, String content, String msgType) {
        Message message = new Message();
        message.setFromUserId(fromUserId);
        message.setToUserId(toUserId);
        message.setContent(content);
        message.setMsgType(msgType != null ? msgType : "text");
        message.setIsRead(0);
        message.setStatus(0);
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public void markAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message != null && message.getIsRead() == 0) {
            message.setIsRead(1);
            message.setReadTime(LocalDateTime.now());
            messageRepository.save(message);
        }
    }

    @Override
    @Transactional
    public void markChatAsRead(Long fromUserId, Long toUserId) {
        List<Message> unreadMessages = messageRepository.findAll().stream()
                .filter(m -> m.getFromUserId().equals(fromUserId) &&
                        m.getToUserId().equals(toUserId) &&
                        m.getIsRead() == 0)
                .collect(Collectors.toList());

        for (Message message : unreadMessages) {
            message.setIsRead(1);
            message.setReadTime(LocalDateTime.now());
        }
        messageRepository.saveAll(unreadMessages);
    }

    @Override
    public Long getUnreadCount(Long userId) {
        return messageRepository.findAll().stream()
                .filter(m -> m.getToUserId().equals(userId) && m.getIsRead() == 0)
                .count();
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message != null) {
            message.setStatus(1);
            messageRepository.save(message);
        }
    }
}
