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

    @GetMapping("/posts")
    public List<PostEntity> findPostList() {
        return postService.findPostList();
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(AuthInfo authInfo,
                                             @RequestBody PostRequest postRequest) throws IllegalAccessException {

        PostEntity postEntity = postService.createPost(postRequest.getTitle(), postRequest.getContent(), authInfo.getMemberId());
        return ResponseEntity.ok(PostResponse.from(postEntity));
    }

    @PostMapping("/posts/{postId}")
    public ResponseEntity<String> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequest postRequest) {

        postService.updatePost(postId, postRequest);
        return ResponseEntity.ok("변경 성공.");
    }

    @GetMapping("/{postId}/delete")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId) {

        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/posts/like/{postId}")
    public ResponseEntity<String> likePost(
            AuthInfo authInfo,
            @PathVariable Long postId) throws IllegalAccessException {
        return ResponseEntity.ok(postService.updateLikeOfPost(postId, authInfo.getMemberId()));
    }
}