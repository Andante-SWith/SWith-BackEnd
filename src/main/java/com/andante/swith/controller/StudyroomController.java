package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.common.dto.StudyroomDto;
import com.andante.swith.entity.Studyroom;
import com.andante.swith.entity.Studyroom_Hashtag;
import com.andante.swith.entity.User;
import com.andante.swith.repository.StudyroomRepository;
import com.andante.swith.repository.Studyroom_HashtagRepository;
import com.andante.swith.service.StudyroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudyroomController {

//    private final StudyroomService studyroomService;
    private final StudyroomRepository studyroomRepository;
    private final Studyroom_HashtagRepository studyroom_hashtagRepository;

    @GetMapping("/studyrooms")
    public ResponseEntity<ResponseDto> getStudyrooms() {
        List<Studyroom> studyrooms = studyroomRepository.findAll();
        return ResponseEntity.ok()
                .body(ResponseDto.success(studyrooms));
    }

    @GetMapping("/studyrooms/{studyroom_id}")
    public ResponseEntity<ResponseDto> getStudyroom(@PathVariable("studyroom_id") Long studyroomId) {
        Studyroom studyroom = studyroomRepository.findById(studyroomId).get();
        return ResponseEntity.ok()
                .body(ResponseDto.success(studyroom));
    }

    @PostMapping("/studyrooms")
    public ResponseEntity<ResponseDto> saveStudyroom(@RequestBody StudyroomDto studyroomDto) {
        Long studyroomId;
        if(studyroomDto.getSecret()==0) {
            Studyroom studyroom = Studyroom.builder()
                    .title(studyroomDto.getTitle())
                    .purpose(studyroomDto.getPurpose())
                    .notice(studyroomDto.getNotice())
                    .secret(studyroomDto.getSecret())
                    .endDate(Timestamp.valueOf(studyroomDto.getEndDate()))
                    .createdDate(new Timestamp(System.currentTimeMillis()))
                    .maxUserCount(4)
                    .build();
            studyroom.createHashtag();
            for(String hashtag:studyroomDto.getHashtag()) {
                studyroom.getHashtags().add(Studyroom_Hashtag.builder()
                        .studyroom(studyroom)
                        .hashtag(hashtag)
                        .build());
            }
            studyroomId = studyroomRepository.save(studyroom).getId();
        }
        else {
            Studyroom studyroom = Studyroom.builder()
                    .title(studyroomDto.getTitle())
                    .purpose(studyroomDto.getPurpose())
                    .notice(studyroomDto.getNotice())
                    .secret(studyroomDto.getSecret())
                    .endDate(Timestamp.valueOf(studyroomDto.getEndDate()))
                    .createdDate(new Timestamp(System.currentTimeMillis()))
                    .maxUserCount(4)
                    .build();
            studyroom.createHashtag();
            for(String hashtag:studyroomDto.getHashtag()) {
                studyroom.getHashtags().add(Studyroom_Hashtag.builder()
                        .studyroom(studyroom)
                        .hashtag(hashtag)
                        .build());
            }
            studyroomId = studyroomRepository.save(studyroom).getId();
        }
        return ResponseEntity.ok()
                .body(ResponseDto.success(studyroomId));
    }

    @PatchMapping("/studyrooms/{studyroom_id}")
    public ResponseEntity<ResponseDto> updateStudyroom(@PathVariable("studyroom_id") Long studyroomId, @RequestBody StudyroomDto studyroomDto) {
        Studyroom findStudyroom = studyroomRepository.findById(studyroomId).get();
        findStudyroom.updateStudyroom(studyroomDto.getTitle(),studyroomDto.getPurpose(),studyroomDto.getSecret(),studyroomDto.getPassword(),studyroomDto.getNotice(),Timestamp.valueOf(studyroomDto.getEndDate()));
        findStudyroom.getHashtags().clear();
        for(String hashtag:studyroomDto.getHashtag()) {
            findStudyroom.getHashtags().add(Studyroom_Hashtag.builder()
                    .studyroom(findStudyroom)
                    .hashtag(hashtag)
                    .build());
        }
        studyroomRepository.save(findStudyroom);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @DeleteMapping("/studyrooms/{studyroom_id}")
    public ResponseEntity<ResponseDto> deleteStudyroom(@PathVariable("studyroom_id") Long studyroomId) {
        studyroomRepository.deleteById(studyroomId);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }



}

