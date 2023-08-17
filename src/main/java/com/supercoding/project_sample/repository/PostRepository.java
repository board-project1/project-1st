package com.supercoding.project_sample.repository;

import com.supercoding.project_sample.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

//    @Query("select a from PostEntity a where a.author = ?1")
//@Query("SELECT p FROM PostEntity p WHERE p.author.email = :email")
List<PostEntity> findAllByAuthor_Email(String email);

}
