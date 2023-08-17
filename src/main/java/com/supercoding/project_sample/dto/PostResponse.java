package com.supercoding.project_sample.dto;

import com.supercoding.project_sample.domain.PostEntity;
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

}
