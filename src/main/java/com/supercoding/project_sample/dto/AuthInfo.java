package com.supercoding.project_sample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(staticName = "of")
public class AuthInfo {
    private Long memberId;

}
