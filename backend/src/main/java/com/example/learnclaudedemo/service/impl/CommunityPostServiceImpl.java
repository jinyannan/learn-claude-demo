package com.example.learnclaudedemo.service.impl;

import com.example.learnclaudedemo.dto.CommentCreateRequest;
import com.example.learnclaudedemo.dto.PostCreateRequest;
import com.example.learnclaudedemo.entity.CommunityPost;
import com.example.learnclaudedemo.entity.PostComment;
import com.example.learnclaudedemo.repository.CommunityPostRepository;
import com.example.learnclaudedemo.repository.PostCommentRepository;
import com.example.learnclaudedemo.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityPostRepository postRepository;
    private final PostCommentRepository commentRepository;

    @Override
    public List<CommunityPost> findAll() {
        return postRepository.findAll();
    }

    @Override
    public CommunityPost findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<CommunityPost> findByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public List<CommunityPost> findByPetId(Long petId) {
        return postRepository.findByPetId(petId);
    }

    @Override
    public List<CommunityPost> findByPostType(String postType) {
        return postRepository.findByPostType(postType);
    }

    @Override
    public List<CommunityPost> findPublishedPosts(Integer page, Integer size) {
        return postRepository.findByStatus(0);
    }

    @Override
    @Transactional
    public CommunityPost create(PostCreateRequest request) {
        CommunityPost post = new CommunityPost();
        post.setUserId(request.getUserId());
        post.setPetId(request.getPetId());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImages(request.getImages());
        post.setPostType(request.getPostType());
        post.setTags(request.getTags());
        post.setLocation(request.getLocation());
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setViewCount(0);
        post.setIsTop(0);
        post.setStatus(0);
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public CommunityPost update(Long id, PostCreateRequest request) {
        CommunityPost post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return null;
        }
        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getImages() != null) {
            post.setImages(request.getImages());
        }
        if (request.getTags() != null) {
            post.setTags(request.getTags());
        }
        if (request.getLocation() != null) {
            post.setLocation(request.getLocation());
        }
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void toggleLike(Long postId, Long userId) {
        CommunityPost post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            Integer currentCount = post.getLikeCount();
            post.setLikeCount(currentCount == null ? 1 : currentCount + 1);
            postRepository.save(post);
        }
    }

    @Override
    public List<PostComment> getComments(Long postId) {
        return commentRepository.findAll().stream()
                .filter(c -> c.getPostId().equals(postId))
                .toList();
    }

    @Override
    @Transactional
    public PostComment addComment(CommentCreateRequest request) {
        PostComment comment = new PostComment();
        comment.setPostId(request.getPostId());
        comment.setUserId(request.getUserId());
        comment.setContent(request.getContent());
        comment.setParentId(request.getParentId());
        comment.setReplyToUserId(request.getReplyToUserId());
        comment.setLikeCount(0);
        comment.setStatus(0);

        PostComment saved = commentRepository.save(comment);

        CommunityPost post = postRepository.findById(request.getPostId()).orElse(null);
        if (post != null) {
            Integer currentCount = post.getCommentCount();
            post.setCommentCount(currentCount == null ? 1 : currentCount + 1);
            postRepository.save(post);
        }

        return saved;
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        PostComment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            CommunityPost post = postRepository.findById(comment.getPostId()).orElse(null);
            if (post != null && post.getCommentCount() != null && post.getCommentCount() > 0) {
                post.setCommentCount(post.getCommentCount() - 1);
                postRepository.save(post);
            }
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long postId) {
        CommunityPost post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            Integer currentCount = post.getViewCount();
            post.setViewCount(currentCount == null ? 1 : currentCount + 1);
            postRepository.save(post);
        }
    }
}
