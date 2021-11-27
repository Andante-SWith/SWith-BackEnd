package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.entity.Board;
import com.andante.swith.entity.Post;
import com.andante.swith.entity.User;
import com.andante.swith.repository.BoardRepository;
import com.andante.swith.repository.CommentRepository;
import com.andante.swith.repository.PostRepository;
import com.andante.swith.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @PostMapping("/boards/{board_id}/posts")
    public ResponseEntity<ResponseDto> savePost(@PathVariable("board_id") Long boardId, @RequestBody Map<String,String> param ) throws UnsupportedEncodingException {
        User user = userRepository.findById(Long.parseLong(param.get("userId"))).get();
        Board board = boardRepository.findById(boardId).get();
        postRepository.save(Post.builder()
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .title(URLDecoder.decode(param.get("title"), "utf-8"))
                .contents(URLDecoder.decode(param.get("content"), "utf-8"))
                .board(board)
                .user(user)
                .viewCount(0)
                .build());
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @GetMapping("/boards/{board_id}/posts")
    public ResponseEntity<ResponseDto> getPosts(@PathVariable("board_id") Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        List<Post> posts = postRepository.findByBoard(board);
        return ResponseEntity.ok()
                .body(ResponseDto.success(posts));
    }

    @DeleteMapping("/boards/{board_id}/posts/{post_id}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId) {
        Board board = boardRepository.findById(boardId).get();
        Post post = postRepository.findByBoardAndId(board, postId).get();
        postRepository.delete(post);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @PutMapping("/boards/{board_id}/posts/{post_id}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @RequestBody Map<String,String> param) throws UnsupportedEncodingException {
        Board board = boardRepository.findById(boardId).get();
        Post post = postRepository.findByBoardAndId(board, postId).get();
        post.updatePost(URLDecoder.decode(param.get("title"), "utf-8"),URLDecoder.decode(param.get("content"), "utf-8"));
        postRepository.save(post);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @GetMapping("/boards/{board_id}/posts/{post_id}")
    public ResponseEntity<ResponseDto> getPost(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId) {
        Board board = boardRepository.findById(boardId).get();
        Post post = postRepository.findByBoardAndId(board, postId).get();
        post.viewCountPlus();
        postRepository.save(post);
        return ResponseEntity.ok()
                .body(ResponseDto.success(post));
    }
}
