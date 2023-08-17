package com.supercoding.project_sample.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime ;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class PostEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    private String title;

    private String content;

    private String nickname;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @Column
    private int liked; // 좋아요 수

    public PostEntity(
                      String title,
                      String content,
                      UserEntity author,
                      LocalDateTime createAt,
                      LocalDateTime updateAt
    ) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.liked = 0;
    }

    public void increaseLikeCount() {
        this.liked += 1;
    }

    public void decreaseLikeCount() {
        this.liked -= 1;
    }
}