package com.supercoding.project_sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private int id;
    private String title;
    private String content;
    private String author;
    private Instant createAt;
    private Instant updateAt;
}