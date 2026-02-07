package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.CommunityPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityPostMapper {
    CommunityPost selectById(@Param("id") Long id);

    List<CommunityPost> selectByUserId(@Param("userId") Long userId);

    List<CommunityPost> selectAll();

    int insert(CommunityPost post);

    int update(CommunityPost post);

    int deleteById(@Param("id") Long id);
}
