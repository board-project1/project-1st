package com.supercoding.project_sample.repository;

import com.supercoding.project_sample.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
