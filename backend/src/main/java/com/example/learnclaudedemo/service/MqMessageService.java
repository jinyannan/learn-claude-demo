package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.entity.MqMessageLog;

public interface MqMessageService {
    void saveMessage(String messageContent, String channel);
}
