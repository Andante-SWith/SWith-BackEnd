package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.common.dto.StudyplannerDto;
import com.andante.swith.entity.Board;
import com.andante.swith.entity.Studyplanner;
import com.andante.swith.entity.Studyplanner_Task;
import com.andante.swith.entity.User;
import com.andante.swith.repository.BoardRepository;
import com.andante.swith.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @PostMapping("/boards")
    public ResponseEntity<ResponseDto> saveBoard(@RequestBody Map<String,String> param ) throws UnsupportedEncodingException {
        User user = userRepository.findById(Long.parseLong(param.get("userId"))).get();
        boardRepository.save(Board.builder()
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .title(URLDecoder.decode(param.get("title"), "utf-8"))
                .user(user)
                .build());
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @GetMapping("/boards")
    public ResponseEntity<ResponseDto> getBoards() {
        List<Board> boards = boardRepository.findAll();
        return ResponseEntity.ok()
                .body(ResponseDto.success(boards));
    }

    @DeleteMapping("/boards/{board_id}")
    public ResponseEntity<ResponseDto> deleteBoard(@PathVariable("board_id") Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        boardRepository.delete(board);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

}
