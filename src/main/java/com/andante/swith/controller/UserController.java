package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.config.security.JwtTokenProvider;
import com.andante.swith.entity.Post;
import com.andante.swith.entity.Report;
import com.andante.swith.entity.Studyplanner;
import com.andante.swith.entity.User;
import com.andante.swith.repository.FollowRepository;
import com.andante.swith.repository.PostRepository;
import com.andante.swith.repository.StudyplannerRepository;
import com.andante.swith.repository.UserRepository;
import com.andante.swith.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final StudyplannerRepository studyplannerRepository;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;


    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody Map<String, String> param) throws UnsupportedEncodingException {

        User user = userRepository.save(User.builder()
                .email(param.get("email"))
                .password(passwordEncoder.encode(param.get("password")))
                .nickname(URLDecoder.decode(param.get("nickname"), "utf-8"))
                .banned((short) 0)
                .deleted((short) 0)
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build());
        studyplannerRepository.save(Studyplanner.builder().user(user).build());
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @PostMapping("/signup/check-email")
    public ResponseEntity<ResponseDto> checkEmail(@RequestBody Map<String,String> param) {
        if(userRepository.findByEmail(param.get("email")).isPresent()==true) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ResponseDto.fail("409","중복된 이메일"));
        }
        else {
            return ResponseEntity.ok()
                    .body(ResponseDto.success(null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody Map<String,String> param) {
        Map result = new HashMap<String,Object>();
        User user = userRepository.findByEmail(param.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(param.get("password"), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        result.put("jwt",jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
        result.put("userId",user.getId());
        return ResponseEntity.ok()
                .body(ResponseDto.success(result));
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable("user_id") Long userId) {
        User user = userRepository.findById(userId).get();
        return ResponseEntity.ok()
                .body(ResponseDto.success(user));
    }
    @GetMapping("/users")
    public ResponseEntity<ResponseDto> getUsers() {
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok()
                .body(ResponseDto.success(all));
    }

    @PatchMapping("/users/{user_id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable("user_id") Long userId, @RequestBody Map<String,String> param) throws UnsupportedEncodingException {
        User user = userRepository.findById(userId).get();

        //닉네임만 수정
        if(param.get("beforePassword").equals("")&&param.get("password").equals("")) {
            user.changeNickname(URLDecoder.decode(param.get("nickname"), "utf-8"));
            userRepository.save(user);
            return ResponseEntity.ok()
                    .body(ResponseDto.success(null));
        }
        //기존의 비밀번호 다르면 401 error
        if (!param.get("beforePassword").equals("")&&!passwordEncoder.matches(param.get("beforePassword"), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseDto.fail("401", "기존 비밀번호 잘못 입력"));
        }
        else {
            user.changePassword(passwordEncoder.encode(param.get("password")));
            user.changeNickname(URLDecoder.decode(param.get("nickname"), "utf-8"));
            userRepository.save(user);
            return ResponseEntity.ok()
                    .body(ResponseDto.success(null));
        }
    }

    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable("user_id") Long userId) {
        User user = userRepository.findById(userId).get();
        userRepository.delete(user);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @PostMapping("/users/report")
    public ResponseEntity<ResponseDto> reportUser(@RequestBody Map<String,Long> param) {
        User user = userRepository.findById(param.get("userId")).get();
        User reportingUser = userRepository.findById(param.get("reportingUserId")).get();
        Report report = Report.builder()
                .reportingUser(reportingUser)
                .user(user)
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .build();
        user.getReporteds().add(report);
        userRepository.save(user);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @GetMapping("/user-info/{user_id}")
    public ResponseEntity<ResponseDto> getPostAndFollow(@PathVariable("user_id") Long userId) {
        Map result = new HashMap<String,Object>();
        User user = userRepository.findById(userId).get();
        int postCount = postRepository.findByUser(user).size();
        int followingCount = followRepository.findByFromUserId(userId).stream().filter(follow -> follow.getApprove().equals(new Integer(1))).toArray().length;
        result.put("postCount", postCount);
        result.put("followingCount", followingCount);
        return ResponseEntity.ok()
                .body(ResponseDto.success(result));
    }
}
