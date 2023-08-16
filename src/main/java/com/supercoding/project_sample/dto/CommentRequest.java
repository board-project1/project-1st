package com.supercoding.project_sample.dto;

import lombok.Getter;

@Getter
public class CommentRequest {
    String author;
    String content;
    Long postId;
}
