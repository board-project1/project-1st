package com.board.project_sample.dto;

import com.board.project_sample.domain.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class CommentResponse {
    private Long commentId;
    private Long postId;
    private Long userId;
    private String nickname;
    private String content;
    private LocalDateTime createAt;
    private String author;

    public static CommentResponse from(CommentEntity commentEntity) {
        return CommentResponse.builder()
                .commentId(commentEntity.getId())
                .postId(commentEntity.getPostId().getId())
                .userId(commentEntity.getAuthor().getId())
                .author(commentEntity.getAuthor().getEmail())
                .nickname(commentEntity.getNickname())
                .content(commentEntity.getContent())
                .createAt(commentEntity.getCreateAt())
                .build();
    }
}
