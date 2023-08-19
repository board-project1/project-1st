package com.supercoding.project_sample.controller;

import com.supercoding.project_sample.domain.CommentEntity;
import com.supercoding.project_sample.dto.AuthInfo;
import com.supercoding.project_sample.dto.CommentRequest;
import com.supercoding.project_sample.dto.CommentResponse;
import com.supercoding.project_sample.exception.IllegalAccessException;
import com.supercoding.project_sample.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

//    // 댓글 생성
//    @PostMapping("/comments/{postId}")
//    public ResponseEntity<CommentResponse> createComment(AuthInfo authInfo,
//                                                         @PathVariable Long postId,
//                                                         @RequestBody CommentRequest commentRequest) throws IllegalAccessException {
//        CommentEntity commentEntity = commentService.createComment(
//                commentRequest.getContent(), postId, authInfo.getMemberId());
//        return ResponseEntity.ok(CommentResponse.from(commentEntity));
//    }
//
//    // 댓글 수정
//    @PutMapping("/comments/{commentId}")
//    public ResponseEntity<CommentResponse> updateComment(AuthInfo authInfo,
//                                                         @PathVariable Long commentId,
//                                                         @RequestBody CommentRequest commentRequest) throws IllegalAccessException {
//        CommentEntity commentEntity = commentService.updateComment(
//                commentRequest.getContent(), commentRequest.getPostId(), authInfo.getMemberId(), commentId
//        );
//        return ResponseEntity.ok(CommentResponse.from(commentEntity));
//    }

    // 댓글 삭제 API
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(AuthInfo authInfo,
                                                         @PathVariable Long commentId,
                                                         @RequestBody CommentRequest commentRequest) throws IllegalAccessException {
        commentService.deleteComment(commentRequest.getPostId(), authInfo.getMemberId(), commentId);

        return ResponseEntity.ok("댓글 삭제 성공");
    }

//    // 댓글 조회
//    @GetMapping("/comments/{postId}")
//    public List<CommentEntity> findPostIdComment(@PathVariable Long postId) {
//        return commentService.findCommentList(postId);
//    }


    // 노션 기준

    // 전체 댓글 조회 API
    @GetMapping("/comments")
    public List<CommentEntity> findCommentAll() {
        return commentService.findCommentAll();
    }

    // 댓글 생성 API
    @PostMapping("/comments")
    public ResponseEntity<CommentResponse> createComment2(AuthInfo authInfo,
                                                          @RequestBody CommentRequest commentRequest) throws IllegalAccessException {
        CommentEntity commentEntity = commentService.createComment2 (
                commentRequest.getContent(), commentRequest.getNickname(), commentRequest.getPostId(), authInfo.getMemberId());
        return ResponseEntity.ok(CommentResponse.from(commentEntity));
    }

    // 댓글 수정 API
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment2(AuthInfo authInfo,
                                                         @PathVariable Long commentId,
                                                         @RequestBody CommentRequest commentRequest) throws IllegalAccessException {
        CommentEntity commentEntity = commentService.updateComment2(
                commentRequest.getContent(), authInfo.getMemberId(), commentId
        );
        return ResponseEntity.ok(CommentResponse.from(commentEntity));
    }


}
