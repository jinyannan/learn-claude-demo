package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByFromUserIdOrderByCreateTimeDesc(Long fromUserId);

    List<Message> findByToUserIdOrderByCreateTimeDesc(Long toUserId);

    List<Message> findByFromUserIdAndToUserIdOrderByCreateTimeDesc(Long fromUserId, Long toUserId);

    List<Message> findByToUserIdAndIsReadOrderByCreateTimeDesc(Long toUserId, Integer isRead);

}
