package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping("/signup/recieve-certificate-code")
    public ResponseDto createCertificateCode(@RequestBody Map<String,String> param) {
        String key = certificationService.sendCertificationCode(param.get("email"));
        certificationService.certificationRegister(param.get("email"),key);
        return ResponseDto.success(null);
    }

    @PostMapping("/signup/certificate-email")
    public ResponseDto certificate(@RequestBody Map<String,String> param) {
        Boolean check = certificationService.selectCertificationByEmail(param.get("email"), param.get("certificationCode"));
        if(check==true)
            return ResponseDto.success(null);
        else
            return ResponseDto.fail("401","올바르지 않은 인증번호");
    }
}
