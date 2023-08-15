package com.supercoding.project_sample.controller;

import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.domain.UserEntity;
import com.supercoding.project_sample.dto.PostRequest;
import com.supercoding.project_sample.response.Response;
import com.supercoding.project_sample.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> createPost(
            @RequestBody PostRequest postRequest) {

        postService.createPost(postRequest);
        return ResponseEntity.ok("생성 성공.");
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

    @PostMapping("/posts/like/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response likePost(@PathVariable Long id,
                             UserEntity userEntity) {
        return Response.success(postService.updateLikeOfPost(id, userEntity));
    }




}