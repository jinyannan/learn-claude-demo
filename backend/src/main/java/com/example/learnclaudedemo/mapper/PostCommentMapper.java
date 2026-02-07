package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.PostComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PostCommentMapper {
    PostComment selectById(@Param("id") Long id);

    List<PostComment> selectByPostId(@Param("postId") Long postId);

    List<PostComment> selectByUserId(@Param("userId") Long userId);

    int insert(PostComment comment);

    int update(PostComment comment);

    int deleteById(@Param("id") Long id);
}
