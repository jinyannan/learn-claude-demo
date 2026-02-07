package com.example.learnclaudedemo.mq;

import com.example.learnclaudedemo.service.MqMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqMessageListener {

    private final MqMessageService mqMessageService;

    public void handleMessage(String message, String queueName) {
        log.info("Received message from {}: {}", queueName, message);
        mqMessageService.saveMessage(message, queueName);
    }
}
