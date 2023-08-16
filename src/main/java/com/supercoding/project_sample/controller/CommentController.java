package com.supercoding.project_sample.controller;

import com.supercoding.project_sample.domain.CommentEntity;
import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.dto.AuthInfo;
import com.supercoding.project_sample.dto.CommentRequest;
import com.supercoding.project_sample.dto.CommentResponse;
import com.supercoding.project_sample.exception.IllegalAccessException;
import com.supercoding.project_sample.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments")
    public List<CommentEntity> findCommentList() {
        return commentService.findCommentList();
    }

    @PostMapping("/comments/{postId}")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId,
                                                         AuthInfo authInfo,
                                                         @RequestBody CommentRequest commentRequest) throws IllegalAccessException {
        CommentEntity commentEntity = commentService.createComment(
                commentRequest.getContent(), postId, authInfo.getMemberId());
        return ResponseEntity.ok(CommentResponse.from(commentEntity));
    }
}
