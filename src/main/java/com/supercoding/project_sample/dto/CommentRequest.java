package com.supercoding.project_sample.dto;

import lombok.Getter;

@Getter
public class CommentRequest {
    String nickname;
    String content;
    Long postId;
}
