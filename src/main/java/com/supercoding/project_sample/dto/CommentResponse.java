package com.supercoding.project_sample.dto;

import com.supercoding.project_sample.domain.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Getter
public class CommentResponse {
    private Long commentId;
    private Long postId;
    private Long userId;
    private String author;
    private String content;
    private Instant createAt;

    public static CommentResponse from(CommentEntity commentEntity) {
        return CommentResponse.builder()
                .commentId(commentEntity.getId())
                .postId(commentEntity.getPostId().getId())
                .userId(commentEntity.getAuthor().getId())
                .author(commentEntity.getAuthor().getEmail())
                .content(commentEntity.getContent())
                .createAt(commentEntity.getCreateAt())
                .build();
    }
}
