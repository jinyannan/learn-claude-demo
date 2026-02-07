package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.UserBehavior;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserBehaviorMapper {
    List<UserBehavior> selectByUserId(@Param("userId") Long userId);

    List<UserBehavior> selectByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    int insert(UserBehavior behavior);
}
