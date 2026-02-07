package com.example.learnclaudedemo.service.impl;

import com.example.learnclaudedemo.entity.MqMessageLog;
import com.example.learnclaudedemo.mapper.MqMessageLogMapper;
import com.example.learnclaudedemo.service.MqMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqMessageServiceImpl implements MqMessageService {

    private final MqMessageLogMapper mqMessageLogMapper;

    @Override
    @Transactional
    public void saveMessage(String messageContent, String channel) {
        log.info("Saving message from channel: {}", channel);
        MqMessageLog mqMessageLog = new MqMessageLog();
        mqMessageLog.setMessageContent(messageContent);
        mqMessageLog.setChannel(channel);
        mqMessageLogMapper.insert(mqMessageLog);
        log.info("Message saved with ID: {}", mqMessageLog.getId());
    }
}
