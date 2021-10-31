package com.andante.swith.service;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    private static String getSHA256(String src) {
//        String sha256 = "";
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            md.update(src.getBytes());
//            byte[] byteData = md.digest();
//            sha256 = Hex.encodeHexString(byteData);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            sha256 = null;
//        }
//        return sha256;
//    }

}
