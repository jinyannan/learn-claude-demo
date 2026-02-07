package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.FriendRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FriendRelationMapper {
    FriendRelation selectById(@Param("id") Long id);

    List<FriendRelation> selectByUserId(@Param("userId") Long userId);

    FriendRelation selectByUserAndFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    int insert(FriendRelation relation);

    int update(FriendRelation relation);

    int deleteById(@Param("id") Long id);
}
