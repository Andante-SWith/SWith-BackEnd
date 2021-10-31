package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.config.security.JwtTokenProvider;
import com.andante.swith.entity.User;
import com.andante.swith.repository.UserRepository;
import com.andante.swith.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseDto<Map> signup(@RequestBody Map<String, String> param) {
        userRepository.save(User.builder()
                .email(param.get("email"))
                .password(passwordEncoder.encode(param.get("password")))
                .nickname(param.get("nickname"))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build());
        return ResponseDto.success(null);
    }

    @PostMapping("/signup/check-email")
    public ResponseDto checkEmail(@RequestBody Map<String,String> param) {
        if(userRepository.findByEmail(param.get("email")).isPresent()==true) {
            return ResponseDto.fail("409", "중복된 이메일이 이미 존재합니다.");
        }
        else {
            return ResponseDto.success(null);
        }
    }

    @PostMapping("/login")
    public ResponseDto<Map> login(@RequestBody Map<String,String> param) {
        Map result = new HashMap<String,Object>();
        User member = userRepository.findByEmail(param.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(param.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        result.put("jwt",jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        result.put("userId",member.getId());
        return ResponseDto.success(result);
    }

    @GetMapping("/users")
    public ResponseDto<List<User>> getUsers() {
        return ResponseDto.success(userRepository.findAll());
    }

}
