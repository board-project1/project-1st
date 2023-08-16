package com.supercoding.project_sample.dto;

import com.supercoding.project_sample.domain.PostEntity;
import lombok.*;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Getter
public class PostResponse {
    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private Instant createAt;

    public static PostResponse from(PostEntity postEntity) {
        return PostResponse.builder()
                .postId(postEntity.getId())
                .userId(postEntity.getAuthor().getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .createAt(postEntity.getCreateAt())
                .build();
    }

}
