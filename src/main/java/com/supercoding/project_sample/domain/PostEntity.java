package com.supercoding.project_sample.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String title;

    String content;

    String author;

    @Column(name = "created_at")
    Instant createAt;

    @Column(name = "updated_at")
    Instant updateAt;

    @Column(nullable = true)
    private Long liked; // 좋아요 수

    public PostEntity(
                      String title,
                      String content,
                      String author,
                      Instant createAt,
                      Instant updateAt,
                      Long liked) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.liked = 0L;
    }

    public void increaseLikeCount() {
        this.liked += 1;
    }

    public void decreaseLikeCount() {
        this.liked -= 1;
    }
}