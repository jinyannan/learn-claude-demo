package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    Message selectById(@Param("id") Long id);

    List<Message> selectBySenderId(@Param("senderId") Long senderId);

    List<Message> selectByReceiverId(@Param("receiverId") Long receiverId);

    List<Message> selectChatHistory(@Param("user1") Long user1, @Param("user2") Long user2);

    int insert(Message message);

    int update(Message message);

    int deleteById(@Param("id") Long id);
}
