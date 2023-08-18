package com.supercoding.project_sample.service;

import com.supercoding.project_sample.domain.LikePostEntity;
import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.domain.UserEntity;
import com.supercoding.project_sample.dto.LikePostResponse;
import com.supercoding.project_sample.dto.PostRequest;
import com.supercoding.project_sample.dto.PostResponse;
import com.supercoding.project_sample.exception.IllegalAccessException;
import com.supercoding.project_sample.exception.LikeHistoryNotfoundException;
import com.supercoding.project_sample.exception.PostNotFoundException;
import com.supercoding.project_sample.repository.LikePostRepository;
import com.supercoding.project_sample.repository.PostRepository;
import com.supercoding.project_sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    // 전체 게시글 조회 (좋아요 추가)
    @Transactional
    public List<LikePostResponse> getAllPosts(Long userId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        List<PostEntity> postEntities = postRepository.findAll();
        List<LikePostEntity> likePostEntities = likePostRepository.findByUserEntity_Id(user.getId());
        Set<Long> likedPostIds = likePostEntities.stream().map((e) -> e.getPostEntity().getId()).collect(Collectors.toSet());

        return postEntities.stream().map((p) -> {
            LikePostResponse likePostResponse = new LikePostResponse();
            likePostResponse.setId(p.getId());
            likePostResponse.setTitle(p.getTitle());
            likePostResponse.setContent(p.getContent());
            likePostResponse.setAuthor(p.getAuthor().getEmail());
            likePostResponse.setCreateAt(p.getCreateAt());
            likePostResponse.setLikeCount(p.getLiked());
            likePostResponse.setIsLiked(likedPostIds.contains(p.getId()));
            return likePostResponse;
        }).collect(Collectors.toList());
    }


//    public PostEntity createPost(String title, String content, long userId) throws IllegalAccessException {
//        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
//
//        return postRepository.save(
//                PostEntity.builder()
//                        .title(title)
//                        .content(content)
//                        .author(user)
//                        .createAt(LocalDateTime.now())
//                        .build()
//        );
//    }

    // 이메일 조회
    @Transactional
    public List<LikePostResponse> findPostListByEmail(String email) throws IllegalAccessException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(IllegalAccessException::new);
        List<PostEntity> postEntities = postRepository.findAllByAuthor_Email(email);
        List<LikePostEntity> likePostEntities = likePostRepository.findByUserEntity_Id(user.getId());
        Set<Long> likedPostIds = likePostEntities.stream().map((e) -> e.getPostEntity().getId()).collect(Collectors.toSet());

        return postEntities.stream().map((p) -> {
            LikePostResponse likePostResponse = new LikePostResponse();
            likePostResponse.setId(p.getId());
            likePostResponse.setTitle(p.getTitle());
            likePostResponse.setContent(p.getContent());
            likePostResponse.setAuthor(p.getAuthor().getEmail());
            likePostResponse.setCreateAt(p.getCreateAt());
            likePostResponse.setLikeCount(p.getLiked());
            likePostResponse.setIsLiked(likedPostIds.contains(p.getId()));
            return likePostResponse;
        }).collect(Collectors.toList());
    }

    // 게시글 수정
    @Transactional
    public void updatePost(long userId, long postId, PostRequest postRequest) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        PostEntity postEntity = postRepository.findById(postId).orElse(null);

        if (postEntity != null && user.getEmail().equals(postEntity.getAuthor().getEmail())) {
            postEntity.setTitle(postRequest.getTitle());
            postEntity.setContent(postRequest.getContent());
            postEntity.setUpdateAt(LocalDateTime.now());

            postRepository.save(postEntity);
        }
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(long userId, long postId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        PostEntity postEntity = postRepository.findById(postId).orElse(null);

        if (postEntity != null && user.getEmail().equals(postEntity.getAuthor().getEmail())) {
            postRepository.deleteById(postId);
        }
    }

    // 게시글 좋아요
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

    // 좋아요 확인
    private boolean hasLikePost(PostEntity postEntity, UserEntity userEntity) {
        return likePostRepository.findByPostEntityAndUserEntity(postEntity, userEntity)
                .isPresent();
    }

    // 좋아요 추가
    private String createLikePost(PostEntity postEntity, UserEntity userEntity) {
        LikePostEntity likePostEntity = new LikePostEntity(postEntity, userEntity);
        likePostRepository.save(likePostEntity);
        return SUCCESS_LIKE_BOARD;
    }

    // 좋아요 삭제
    private String removeLikePost(PostEntity postEntity, UserEntity userEntity) {
        LikePostEntity likePostEntity = likePostRepository.findByPostEntityAndUserEntity(postEntity, userEntity)
                .orElseThrow(LikeHistoryNotfoundException::new);
        likePostRepository.delete(likePostEntity);
        return SUCCESS_UNLIKE_BOARD;
    }

    // 게시글 생성
    @Transactional
    public PostEntity createPost2(String title, String content, String nickname, Long userId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);

        return postRepository.save(
                PostEntity.builder()
                        .title(title)
                        .content(content)
                        .author(user)
                        .nickname(nickname)
                        .createAt(LocalDateTime.now())
                        .build()
        );
    }
}