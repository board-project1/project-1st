package com.supercoding.project_sample.service;

import com.supercoding.project_sample.domain.CommentEntity;
import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.domain.UserEntity;
import com.supercoding.project_sample.exception.CommentNotFoundException;
import com.supercoding.project_sample.exception.IllegalAccessException;
import com.supercoding.project_sample.exception.PostNotFoundException;
import com.supercoding.project_sample.repository.CommentRepository;
import com.supercoding.project_sample.repository.PostRepository;
import com.supercoding.project_sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Optional<CommentEntity> findCommentList(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public CommentEntity createComment(String content, Long postId, Long userId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        return commentRepository.save(
                CommentEntity.builder()
                        .author(user)
                        .postId(post)
                        .content(content)
                        .createAt(Instant.now())
                        .build()

        );
    }

    @Transactional
    public CommentEntity updateComment(String content, Long postId, Long userId, Long commentId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if (post != null && comment != null && user.getEmail().equals(comment.getAuthor().getEmail())) {
            comment.setContent(content);
            comment.setUpdateAt(Instant.now());

            commentRepository.save(comment);
        }
        return comment;
    }

    public CommentEntity deleteComment(Long postId, Long userId, Long commentId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if (post.getId().equals(postId) && comment.getId().equals(commentId) && user.getEmail().equals(comment.getAuthor().getEmail())) {
            commentRepository.delete(comment);
        }
        return comment;
    }


}
