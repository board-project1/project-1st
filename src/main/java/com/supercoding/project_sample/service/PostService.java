package com.supercoding.project_sample.service;

import com.supercoding.project_sample.domain.LikePostEntity;
import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.domain.UserEntity;
import com.supercoding.project_sample.dto.PostRequest;
import com.supercoding.project_sample.exception.IllegalAccessException;
import com.supercoding.project_sample.exception.LikeHistoryNotfoundException;
import com.supercoding.project_sample.exception.PostNotFoundException;
import com.supercoding.project_sample.repository.LikePostRepository;
import com.supercoding.project_sample.repository.PostRepository;
import com.supercoding.project_sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private static final String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";

    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;
    private final UserRepository userRepository;

    public List<PostEntity> findPostList() {
        return postRepository.findAll();
    }

    public PostEntity createPost(String title, String content, Long userId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);

        return postRepository.save(
                PostEntity.builder()
                        .title(title)
                        .content(content)
                        .author(user)
                        .createAt(Instant.now())
                        .build()
        );
    }

    @Transactional
    public void updatePost(Long postId, PostRequest postRequest) {
        PostEntity postEntity = postRepository.findById(postId).orElse(null);
        if (postEntity != null) {
            postEntity.setTitle(postRequest.getTitle());
            postEntity.setContent(postRequest.getContent());
            postEntity.setUpdateAt(Instant.now());

            postRepository.save(postEntity);
        }
    }

    @Transactional
    public void deletePost(long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElse(null);
        if (postEntity != null) {
            postRepository.deleteById(postId);
        }
    }

    @Transactional
    public String updateLikeOfPost(Long postId, Long userId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);


        if (!hasLikePost(post, user)) {
            post.increaseLikeCount();
            return createLikePost(post, user);
        }

        post.decreaseLikeCount();
        return removeLikePost(post, user);
    }


    private boolean hasLikePost(PostEntity postEntity, UserEntity userEntity) {
        return likePostRepository.findByPostEntityAndUserEntity(postEntity, userEntity)
                .isPresent();
    }
    private String createLikePost(PostEntity postEntity, UserEntity userEntity) {
        LikePostEntity likePostEntity = new LikePostEntity(postEntity, userEntity);
        likePostRepository.save(likePostEntity);
        return SUCCESS_LIKE_BOARD;
    }
    private String removeLikePost(PostEntity postEntity, UserEntity userEntity) {
        LikePostEntity likePostEntity = likePostRepository.findByPostEntityAndUserEntity(postEntity, userEntity)
                .orElseThrow(LikeHistoryNotfoundException::new);
        likePostRepository.delete(likePostEntity);

        return SUCCESS_UNLIKE_BOARD;
    }
}