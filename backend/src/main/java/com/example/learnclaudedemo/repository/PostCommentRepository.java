package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findByPostIdOrderByCreateTimeAsc(Long postId);

    List<PostComment> findByPostIdAndParentIdIsNullOrderByCreateTimeAsc(Long postId);

    List<PostComment> findByUserIdOrderByCreateTimeDesc(Long userId);

    List<PostComment> findByParentIdOrderByCreateTimeAsc(Long parentId);

}
