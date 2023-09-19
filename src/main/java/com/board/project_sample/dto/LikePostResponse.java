package com.board.project_sample.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikePostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createAt;
    private int likeCount;
    private Boolean isLiked;
}
