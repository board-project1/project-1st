package com.supercoding.project_sample.service;

import com.supercoding.project_sample.domain.CommentEntity;
import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.domain.UserEntity;
import com.supercoding.project_sample.exception.IllegalAccessException;
import com.supercoding.project_sample.repository.CommentRepository;
import com.supercoding.project_sample.repository.PostRepository;
import com.supercoding.project_sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<CommentEntity> findCommentList() {
        return commentRepository.findAll();
    }

    public CommentEntity createComment(String content, Long postId, Long userId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post Not Found"));

        return commentRepository.save(
                CommentEntity.builder()
                        .author(user)
                        .postId(post)
                        .content(content)
                        .createAt(Instant.now())
                        .build()

        );
    }
}
