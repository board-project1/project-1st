package com.supercoding.project_sample.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @Column(name = "created_at")
    private Instant createAt;

    @Column(name = "updated_at")
    private Instant updateAt;

    @Column
    private int liked; // 좋아요 수

    public PostEntity(
                      String title,
                      String content,
                      UserEntity author,
                      Instant createAt,
                      Instant updateAt
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