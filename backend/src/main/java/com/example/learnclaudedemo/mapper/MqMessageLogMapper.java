package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.MqMessageLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MqMessageLogMapper {
    int insert(MqMessageLog record);
}
