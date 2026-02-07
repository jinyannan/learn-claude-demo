package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.PetFriendContact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PetFriendContactMapper {
    PetFriendContact selectById(@Param("id") Long id);

    List<PetFriendContact> selectByUserId(@Param("userId") Long userId);

    PetFriendContact selectByUserAndContact(@Param("userId") Long userId, @Param("contactUserId") Long contactUserId);

    int insert(PetFriendContact contact);

    int update(PetFriendContact contact);

    int deleteById(@Param("id") Long id);
}
