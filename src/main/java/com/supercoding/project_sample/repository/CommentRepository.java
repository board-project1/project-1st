package com.supercoding.project_sample.repository;

import com.supercoding.project_sample.domain.CommentEntity;
import com.supercoding.project_sample.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostId(PostEntity postId);
}
