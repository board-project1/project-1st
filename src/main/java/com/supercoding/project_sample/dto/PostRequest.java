package com.supercoding.project_sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private int id;
    private String title;
    private String content;
    private String author;
}