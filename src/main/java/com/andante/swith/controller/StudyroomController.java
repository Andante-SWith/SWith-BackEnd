package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.common.dto.StudyroomDto;
import com.andante.swith.entity.*;
import com.andante.swith.repository.BanUserRepository;
import com.andante.swith.repository.StudyroomRepository;
import com.andante.swith.repository.Studyroom_HashtagRepository;
import com.andante.swith.repository.UserRepository;
import com.andante.swith.service.StudyroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudyroomController {


    private final UserRepository userRepository;
    private final StudyroomRepository studyroomRepository;
    private final Studyroom_HashtagRepository studyroom_hashtagRepository;
    private final BanUserRepository banUserRepository;

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
    public ResponseEntity<ResponseDto> saveStudyroom(@RequestBody StudyroomDto studyroomDto) throws UnsupportedEncodingException {
        Long studyroomId;
        if(studyroomDto.getSecret()==0) {
            Studyroom studyroom = Studyroom.builder()
                    .title(URLDecoder.decode(studyroomDto.getTitle(), "utf-8"))
                    .purpose(URLDecoder.decode(studyroomDto.getPurpose(), "utf-8"))
                    .notice(URLDecoder.decode(studyroomDto.getNotice(), "utf-8"))
                    .secret(studyroomDto.getSecret())
                    .endDate(Timestamp.valueOf(studyroomDto.getEndDate()))
                    .createdDate(new Timestamp(System.currentTimeMillis()))
                    .maxUserCount(studyroomDto.getMaxUserCount())
                    .masterId(studyroomDto.getMasterId())
                    .build();
            studyroom.createHashtag();
            for(String hashtag:studyroomDto.getHashtag()) {
                studyroom.getHashtags().add(Studyroom_Hashtag.builder()
                        .studyroom(studyroom)
                        .hashtag(URLDecoder.decode(hashtag, "utf-8"))
                        .build());
            }
            studyroomId = studyroomRepository.save(studyroom).getId();
        }
        else {
            Studyroom studyroom = Studyroom.builder()
                    .title(URLDecoder.decode(studyroomDto.getTitle(), "utf-8"))
                    .purpose(URLDecoder.decode(studyroomDto.getPurpose(), "utf-8"))
                    .notice(URLDecoder.decode(studyroomDto.getNotice(), "utf-8"))
                    .secret(studyroomDto.getSecret())
                    .endDate(Timestamp.valueOf(studyroomDto.getEndDate()))
                    .createdDate(new Timestamp(System.currentTimeMillis()))
                    .maxUserCount(studyroomDto.getMaxUserCount())
                    .masterId(studyroomDto.getMasterId())
                    .password(studyroomDto.getPassword())
                    .build();
            studyroom.createHashtag();
            for(String hashtag:studyroomDto.getHashtag()) {
                studyroom.getHashtags().add(Studyroom_Hashtag.builder()
                        .studyroom(studyroom)
                        .hashtag(URLDecoder.decode(hashtag, "utf-8"))
                        .build());
            }
            studyroomId = studyroomRepository.save(studyroom).getId();
        }
        return ResponseEntity.ok()
                .body(ResponseDto.success(studyroomId));
    }

    @PatchMapping("/studyrooms/{studyroom_id}")
    public ResponseEntity<ResponseDto> updateStudyroom(@PathVariable("studyroom_id") Long studyroomId, @RequestBody StudyroomDto studyroomDto) throws UnsupportedEncodingException {
        Studyroom findStudyroom = studyroomRepository.findById(studyroomId).get();
        findStudyroom.updateStudyroom(URLDecoder.decode(studyroomDto.getTitle(), "utf-8"),URLDecoder.decode(studyroomDto.getPurpose(), "utf-8"),studyroomDto.getSecret(),studyroomDto.getPassword(),URLDecoder.decode(studyroomDto.getNotice(), "utf-8"),Timestamp.valueOf(studyroomDto.getEndDate()),studyroomDto.getMaxUserCount());
        findStudyroom.getHashtags().clear();
        for(String hashtag:studyroomDto.getHashtag()) {
            findStudyroom.getHashtags().add(Studyroom_Hashtag.builder()
                    .studyroom(findStudyroom)
                    .hashtag(URLDecoder.decode(hashtag, "utf-8"))
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

    @GetMapping("/studyrooms/master/{user_id}")
    public ResponseEntity<ResponseDto> getMasterStudyroom(@PathVariable("user_id") Long masterId) {
        Map result = new HashMap<String,Object>();
        List<Long> ids = new ArrayList<>();
        List<Studyroom> studyrooms = studyroomRepository.findByMasterId(masterId);
        for(Studyroom st:studyrooms) {
            ids.add(st.getId());
        }
        result.put("studyroomIds",ids);
        return ResponseEntity.ok()
                .body(ResponseDto.success(result));
    }

    @PostMapping("/studyrooms/ban-user")
    public ResponseEntity<ResponseDto> banUser(@RequestBody Map<String,Long> param) {
        User user = userRepository.findById(param.get("userId")).get();
        Studyroom studyroom = studyroomRepository.findById(param.get("studyroomId")).get();

        BanUser banUser = banUserRepository.save(BanUser.builder()
                .user(user)
                .studyroom(studyroom)
                .build());
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }
    
    @GetMapping("/studyrooms/ban-user/{studyroom_id}")
    public ResponseEntity<ResponseDto> getBanUser(@PathVariable("studyroom_id") Long studyroomId) {
        Studyroom studyroom = studyroomRepository.findById(studyroomId).get();
        List<BanUser> banUsers = banUserRepository.findByStudyroom(studyroom);
        return ResponseEntity.ok()
                .body(ResponseDto.success(banUsers));
    }
}

