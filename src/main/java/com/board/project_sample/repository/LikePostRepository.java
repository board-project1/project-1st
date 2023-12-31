package com.board.project_sample.repository;

import com.board.project_sample.domain.LikePostEntity;
import com.board.project_sample.domain.PostEntity;
import com.board.project_sample.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikePostRepository extends JpaRepository<LikePostEntity, Long> {
    Optional<LikePostEntity> findByPostEntityAndUserEntity(PostEntity postEntity, UserEntity userEntity);

    List<LikePostEntity> findByUserEntity_Id(Long userId);

    List<LikePostEntity> findByUserEntity_Email(String email);
}
