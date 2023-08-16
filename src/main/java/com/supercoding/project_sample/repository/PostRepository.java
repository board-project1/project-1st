package com.supercoding.project_sample.repository;

import com.supercoding.project_sample.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("select a from PostEntity a where a.author = ?1")
    List<PostEntity> findPostListByEmail(String email);
}
