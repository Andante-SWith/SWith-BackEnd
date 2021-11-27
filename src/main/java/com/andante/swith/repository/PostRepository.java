package com.andante.swith.repository;

import com.andante.swith.entity.Board;
import com.andante.swith.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findByBoardAndId(Board board, Long postId);

    List<Post> findByBoard(Board board);
}
