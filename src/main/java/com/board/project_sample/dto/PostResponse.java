package com.board.project_sample.dto;

import com.board.project_sample.domain.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class PostResponse {
    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private String author;
    private String nickname;
    private LocalDateTime createAt;
    private int liked;
    private Boolean status;

    public static PostResponse from(PostEntity postEntity) {
        return PostResponse.builder()
                .postId(postEntity.getId())
                .userId(postEntity.getAuthor().getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .author(postEntity.getAuthor().getEmail())
                .nickname(postEntity.getNickname())
                .createAt(postEntity.getCreateAt())
                .build();
    }

    public PostResponse(Long postId, String title, String content, LocalDateTime createAt, String author, int liked, Boolean status) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.author = author;
        this.liked = liked;
        this.status = status;
    }
}
