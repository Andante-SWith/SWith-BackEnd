package com.andante.swith.service;

import com.andante.swith.entity.Certification;
import com.andante.swith.repository.CertificationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final JavaMailSender mailSender;
    private final CertificationRepository certificationRepository;

    public String sendCertificationCode(String email) {
        Random random=new Random();  //난수 생성을 위한 랜덤 클래스
        String key="";  //인증번호

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); //스크립트에서 보낸 메일을 받을 사용자 이메일 주소
        message.setFrom("dhtkdals0528@gmail.com");
        //입력 키를 위한 코드
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }
        int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
        key+=numIndex;
        message.setSubject("인증번호 입력을 위한 메일 전송");
        message.setText("인증 번호 : "+key);
        mailSender.send(message);
        return key;
    }

    public Long certificationRegister(String email, String certificationCode) {
        Optional<Certification> certification = certificationRepository.findByEmail(email);
        if(certification.isPresent()==true) {
            certification.get().changeCertificationCode(certificationCode);
            return certificationRepository.save(certification.get()).getId();
        }
        else {
            return certificationRepository.save(Certification.builder()
                    .email(email)
                    .certificationCode(certificationCode)
                    .build()).getId();
        }
    }

    public Boolean selectCertificationByEmail(String email, String certificationCode) {
        if(certificationRepository.findByEmail(email).get().getCertificationCode().equals(certificationCode))
            return true;
        else
            return false;
    }
}
