package com.supercoding.project_sample.controller;

import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.domain.UserEntity;
import com.supercoding.project_sample.dto.AuthInfo;
import com.supercoding.project_sample.dto.PostRequest;
import com.supercoding.project_sample.dto.PostResponse;
import com.supercoding.project_sample.exception.IllegalAccessException;
import com.supercoding.project_sample.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시물 전체 조회하는 API
    @GetMapping("/posts")
    public List<PostEntity> findPostList() {
        return postService.findPostList();
    }

    // 작성자 이메일을 통해 특정 게시물들을 검색하는 API
    @GetMapping("/posts/search")
    public List<PostEntity> findPostByEmail(
            AuthInfo authInfo) throws IllegalAccessException {
        return postService.findPostListByEmail(authInfo.getMemberId());
    }

    // 게시물을 새롭게 만들 수 있는 API
    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(
            AuthInfo authInfo,
            @RequestBody PostRequest postRequest) throws IllegalAccessException {

        PostEntity postEntity = postService.createPost(postRequest.getTitle(), postRequest.getContent(), authInfo.getMemberId());
        return ResponseEntity.ok(PostResponse.from(postEntity));
    }

    // 기존 댓글의 글을 수정하는 API
    @PutMapping("/posts/{postId}")
    public ResponseEntity<String> updatePost(
            AuthInfo authInfo,
            @PathVariable Long postId,
            @RequestBody PostRequest postRequest) throws IllegalAccessException {

        postService.updatePost(authInfo.getMemberId(), postId, postRequest);
        return ResponseEntity.ok("댓글이 수정되었습니다.");
    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<Void> deletePost(
            AuthInfo authInfo,
            @PathVariable Long postId) throws IllegalAccessException {

        postService.deletePost(authInfo.getMemberId(), postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/posts/like/{postId}")
    public ResponseEntity<String> likePost(
            AuthInfo authInfo,
            @PathVariable Long postId) throws IllegalAccessException {
        return ResponseEntity.ok(postService.updateLikeOfPost(postId, authInfo.getMemberId()));
    }
}