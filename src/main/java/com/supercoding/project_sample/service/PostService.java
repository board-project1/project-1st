package com.supercoding.project_sample.service;

import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.dto.PostRequest;
import com.supercoding.project_sample.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostEntity> findPostList() {
        return postRepository.findAll();
    }

    @Transactional
    public void createPost(PostRequest postRequest){
        PostEntity postEntity = PostEntity.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .createAt(Instant.now())
                .updateAt(Instant.now())
                .build();
        postRepository.save(postEntity);
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
}