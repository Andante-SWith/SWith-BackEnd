package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.config.security.JwtTokenProvider;
import com.andante.swith.entity.Report;
import com.andante.swith.entity.User;
import com.andante.swith.repository.UserRepository;
import com.andante.swith.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody Map<String, String> param) {

        userRepository.save(User.builder()
                .email(param.get("email"))
                .password(passwordEncoder.encode(param.get("password")))
                .nickname(param.get("nickname"))
                .banned((short)0)
                .deleted((short)0)
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build());
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
        User member = userRepository.findByEmail(param.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(param.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        result.put("jwt",jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        result.put("userId",member.getId());
        return ResponseEntity.ok()
                .body(ResponseDto.success(result));
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable("user_id") Long userId) {
        User user = userRepository.findById(userId).get();
        return ResponseEntity.ok()
                .body(ResponseDto.success(user));
    }

    @PatchMapping("/users/{user_id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable("user_id") Long userId, @RequestBody Map<String,String> param) {
        User user = userRepository.findById(userId).get();


        user.changePassword(passwordEncoder.encode(param.get("password")));
        user.changeNickname(param.get("nickname"));
        userRepository.save(user);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
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
        User user = userRepository.findById(param.get("user_id")).get();
        User reportingUser = userRepository.findById(param.get("reporting_user_id")).get();
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
}
