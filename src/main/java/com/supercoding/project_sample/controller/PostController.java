package com.supercoding.project_sample.controller;

import com.supercoding.project_sample.domain.PostEntity;
import com.supercoding.project_sample.domain.UserEntity;
import com.supercoding.project_sample.dto.AuthInfo;
import com.supercoding.project_sample.dto.PostRequest;
import com.supercoding.project_sample.dto.PostResponse;
import com.supercoding.project_sample.service.PostService;
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

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시물 전체 조회하는 API
    // GET
    @GetMapping("/posts")
    public List<PostEntity> findPostList() {
        return postService.findPostList();
    }

    // 작성자 이메일을 통해 특정 게시물들을 검색하는 API
    // GET
    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(AuthInfo authInfo,
                                             @RequestBody PostRequest postRequest) throws IllegalAccessException {

        PostEntity postEntity = postService.createPost(postRequest.getTitle(), postRequest.getContent(), authInfo.getMemberId());
        return ResponseEntity.ok(PostResponse.from(postEntity));
    }

    // 작성자 이메일을 통해 특정 게시물들을 검색하는 API
    // GET
    @GetMapping("/posts/search")
    public List<PostEntity> findPostByEmail(
            @RequestParam String email
    ) {
        return postService.findPostListByEmail(email);
    }

    // 게시물을 새롭게 만들 수 있는 API
    // POST
    @PostMapping("/posts")
    public ResponseEntity<String> createPost(
            AuthInfo authinfo,
            @RequestBody PostRequest postRequest) {

        postService.createPost(authinfo, postRequest);
        return ResponseEntity.ok("생성 성공.");
    }

    // 기존 댓글의 글을 수정하는 API
    // PUT
    @PostMapping("/posts/{postId}")
    public ResponseEntity<String> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequest postRequest) {

        postService.updatePost(postId, postRequest);
        return ResponseEntity.ok("변경 성공.");
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        // return 을 json 형태로 변경해야 하는건가??
        // "message" :
    }

    @GetMapping("/{postId}/delete")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId) {

        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/posts/like/{postid}")
    public ResponseEntity<String> likePost(@PathVariable Long postid,
                             UserEntity userEntity) {
        return ResponseEntity.ok(postService.updateLikeOfPost(postid, userEntity));
    }
}