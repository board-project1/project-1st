package com.supercoding.project_sample.dto;

import com.supercoding.project_sample.domain.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long liked;
    private Instant createAt;
    private Instant updateAt;


    public static PostResponse toDto(PostEntity postEntity) {
        return new PostResponse(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getLiked(),
                postEntity.getCreateAt(),
                postEntity.getUpdateAt()
                );
    }
}
