package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.common.dto.StudyplannerDto;
import com.andante.swith.common.dto.StudyroomDto;
import com.andante.swith.entity.Statistic;
import com.andante.swith.entity.Studyplanner;
import com.andante.swith.entity.Studyplanner_Task;
import com.andante.swith.entity.User;
import com.andante.swith.repository.StudyplannerRepository;
import com.andante.swith.repository.Studyplanner_TaskRepository;
import com.andante.swith.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StudyplannerController {

    private final UserRepository userRepository;
    private final StudyplannerRepository studyplannerRepository;
    private final Studyplanner_TaskRepository studyplanner_taskRepository;

    @GetMapping("/planners/{user_id}")
    public ResponseEntity<ResponseDto> getStudyplanner(@PathVariable("user_id") Long userId) {

        Studyplanner studyplanner = studyplannerRepository.findByUser(userRepository.findById(userId).get()).get();
        return ResponseEntity.ok()
                .body(ResponseDto.success(studyplanner));
    }

    @PostMapping("/planners/{user_id}")
    public ResponseEntity<ResponseDto> saveStudyplanner(@PathVariable("user_id") Long userId,@RequestBody StudyplannerDto studyplannerDto ) {
        Map result = new HashMap<String,Object>();
        User user = userRepository.findById(userId).get();
        Studyplanner studyplanner = studyplannerRepository.findByUser(user).get();
        Long studyplannerTaskId = studyplanner_taskRepository.save(Studyplanner_Task.builder()
                .taskDescription(studyplannerDto.getTaskDescription())
                .startDate(studyplannerDto.getStartDate())
                .endDate(studyplannerDto.getEndDate())
                .complete(studyplannerDto.getComplete())
                .studyplanner(studyplanner)
                .build()).getId();
        result.put("taskId",studyplannerTaskId);
        return ResponseEntity.ok()
                .body(ResponseDto.success(result));
    }
}
