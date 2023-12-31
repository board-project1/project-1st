package com.board.project_sample.repository;

import com.board.project_sample.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

//    @Query("select a from PostEntity a where a.author = ?1")
//@Query("SELECT p FROM PostEntity p WHERE p.author.email = :email")
List<PostEntity> findAllByAuthor_Email(String email);


//@Query("SELECT p.id, p.content, p.createAt, p.liked, p.title, l.status " +
//        "FROM PostEntity p LEFT JOIN LikePostEntity l ON p.id = l.postEntity.id " +
//        "LEFT JOIN UserEntity u ON p.author.id = u.id")
//List<PostResponse> findAllPostsWithLikeStatus();

}
