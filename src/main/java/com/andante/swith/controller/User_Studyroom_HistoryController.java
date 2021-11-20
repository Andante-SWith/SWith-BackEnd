package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.entity.Studyroom;
import com.andante.swith.entity.Studyroom_Hashtag;
import com.andante.swith.entity.User;
import com.andante.swith.entity.User_Studyroom_History;
import com.andante.swith.repository.StudyroomRepository;
import com.andante.swith.repository.UserRepository;
import com.andante.swith.repository.User_Studyroom_HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class User_Studyroom_HistoryController {
    private final User_Studyroom_HistoryRepository user_studyroom_historyRepository;
    private final UserRepository userRepository;
    private final StudyroomRepository studyroomRepository;

    @PostMapping("/studyrooms/history")
    public ResponseEntity<ResponseDto> saveHistory(@RequestBody Map<String,Long> param) {
        User user = userRepository.findById(param.get("userId")).get();
        Studyroom studyroom = studyroomRepository.findById(param.get("studyRoomId")).get();

        User_Studyroom_History user_studyroom_history = user_studyroom_historyRepository.save(User_Studyroom_History.builder()
                .user(user)
                .studyroom(studyroom)
                .build());
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @GetMapping("/studyrooms/history/{user_id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable("user_id") Long userId) {
        Map result = new HashMap<String,Object>();
        Set<Long> ids = new HashSet<>();
        User user = userRepository.findById(userId).get();

        List<User_Studyroom_History> history = user_studyroom_historyRepository.findByUser(user);
        for(User_Studyroom_History his:history) {
            ids.add(his.getStudyroom().getId());
        }
        result.put("studyroomIds",ids);
        return ResponseEntity.ok()
                .body(ResponseDto.success(result));
    }
}
