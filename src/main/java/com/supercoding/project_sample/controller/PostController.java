package com.supercoding.project_sample.controller;

import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.dto.PostRequest;
import com.supercoding.project_sample.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

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
}