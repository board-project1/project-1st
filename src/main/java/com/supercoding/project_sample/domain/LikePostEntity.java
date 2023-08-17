package com.supercoding.project_sample.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class LikePostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    // true = 좋아요 false = 좋아요 취소
    @Column
    private boolean status;

    public LikePostEntity(PostEntity postEntity, UserEntity userEntity) {
        this.postEntity = postEntity;
        this.userEntity = userEntity;
        this.status = true;
    }

}
