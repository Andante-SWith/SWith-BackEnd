package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.entity.Board;
import com.andante.swith.entity.Comment;
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
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @PostMapping("/boards/{board_id}/posts/{post_id}/comment")
    public ResponseEntity<ResponseDto> saveComment(@PathVariable("post_id") Long postId, @RequestBody Map<String,String> param) throws UnsupportedEncodingException {
        User user = userRepository.findById(Long.parseLong(param.get("userId"))).get();
        Post post = postRepository.findById(postId).get();
        commentRepository.save(Comment.builder()
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .comment(URLDecoder.decode(param.get("comment"), "utf-8"))
                .user(user)
                .post(post)
                .build());
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @DeleteMapping("/boards/{board_id}/posts/{post_id}/comment/{comment_id}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId) {
        Post post = postRepository.findById(postId).get();
        Comment comment = commentRepository.findByPostAndId(post, commentId).get();
        commentRepository.delete(comment);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @PutMapping("/boards/{board_id}/posts/{post_id}/comment/{comment_id}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId, @RequestBody Map<String,String> param) throws UnsupportedEncodingException {
        Post post = postRepository.findById(postId).get();
        Comment comment = commentRepository.findByPostAndId(post, commentId).get();
        comment.updateComment(URLDecoder.decode(param.get("comment"), "utf-8"));
        commentRepository.save(comment);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }
}
