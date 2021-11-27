package com.andante.swith.repository;

import com.andante.swith.entity.Board;
import com.andante.swith.entity.Comment;
import com.andante.swith.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByPostAndId(Post post, Long commentId);
}
