package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.UserBehavior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBehaviorRepository extends JpaRepository<UserBehavior, Long> {

    List<UserBehavior> findByUserIdOrderByCreateTimeDesc(Long userId);

    List<UserBehavior> findByBehaviorTypeAndTargetTypeAndTargetId(String behaviorType, String targetType, Long targetId);

    List<UserBehavior> findByUserIdAndBehaviorTypeOrderByCreateTimeDesc(Long userId, String behaviorType);

    List<UserBehavior> findByTargetTypeAndTargetIdOrderByCreateTimeDesc(String targetType, Long targetId);

}
