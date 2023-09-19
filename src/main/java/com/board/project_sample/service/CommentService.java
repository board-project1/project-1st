package com.board.project_sample.service;

import com.board.project_sample.exception.CommentNotFoundException;
import com.board.project_sample.exception.IllegalAccessException;
import com.board.project_sample.exception.PostNotFoundException;
import com.board.project_sample.repository.CommentRepository;
import com.board.project_sample.repository.PostRepository;
import com.board.project_sample.repository.UserRepository;
import com.board.project_sample.domain.CommentEntity;
import com.board.project_sample.domain.PostEntity;
import com.board.project_sample.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

//    public List<CommentEntity> findCommentList(Long postId) {
//        PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
//        return commentRepository.findByPostId(post);
//    }
//
//    public CommentEntity createComment(String content, Long postId, Long userId) throws IllegalAccessException {
//        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
//        PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
//
//        return commentRepository.save(
//                CommentEntity.builder()
//                        .author(user)
//                        .postId(post)
//                        .content(content)
//                        .createAt(LocalDateTime.now())
//                        .build()
//        );
//    }
//
//    @Transactional
//    public CommentEntity updateComment(String content, Long postId, Long userId, Long commentId) throws IllegalAccessException {
//        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
//        PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
//        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
//
//        if (post.getId().equals(postId) && user.getId().equals(comment.getAuthor().getId())) {
//            comment.setContent(content);
//            comment.setUpdateAt(LocalDateTime.now());
//
//            commentRepository.save(comment);
//        }
//        return comment;
//    }

    // 댓글 삭제
    public CommentEntity deleteComment(Long postId, Long userId, Long commentId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if (post.getId().equals(postId) && comment.getId().equals(commentId) && user.getEmail().equals(comment.getAuthor().getEmail())) {
            commentRepository.delete(comment);
        }
        return comment;
    }


    // 댓글 조회
    public List<CommentEntity> findCommentAll() {
        return commentRepository.findAll();
    }


    // 노션
    // 댓글 생성
    @Transactional
    public CommentEntity createComment2(String content, String nickname, Long postId, Long userId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        PostEntity post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return commentRepository.save(
                CommentEntity.builder()
                        .nickname(nickname)
                        .author(user)
                        .postId(post)
                        .content(content)
                        .createAt(LocalDateTime.now())
                        .build()
        );
    }

    // 댓글 수정
    @Transactional
    public CommentEntity updateComment2(String content, Long userId, Long commentId) throws IllegalAccessException {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalAccessException::new);
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if (user.getId().equals(comment.getAuthor().getId())) {
            comment.setContent(content);
            comment.setUpdateAt(LocalDateTime.now());

            commentRepository.save(comment);
        }
        return comment;
    }
}
