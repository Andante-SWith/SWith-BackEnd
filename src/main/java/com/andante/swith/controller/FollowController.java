package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.entity.Follow;
import com.andante.swith.entity.User;
import com.andante.swith.repository.FollowRepository;
import com.andante.swith.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @GetMapping("/followers/{to_user_id}")
    public ResponseEntity<ResponseDto> getFollowers(@PathVariable("to_user_id") Long userId) {
        List<Follow> follows = followRepository.findByToUserId(userId);
        Map result = new HashMap<String, Object>();
        List<Object> users = new ArrayList<>();
        for (Follow follow : follows) {
            Map userInfo = new HashMap<String,Object>();
            User findUser = userRepository.findById(follow.getFromUserId()).get();
            userInfo.put("approve",follow.getApprove());
            userInfo.put("id",findUser.getId());
            userInfo.put("imageURL",findUser.getImageURL());
            userInfo.put("nickname",findUser.getNickname());
            users.add(userInfo);
        }
        result.put("users", users);
        return ResponseEntity.ok()
                .body(ResponseDto.success(result));
    }

    @GetMapping("/followings/{from_user_id}")
    public ResponseEntity<ResponseDto> getFollowings(@PathVariable("from_user_id") Long userId) {
        List<Follow> follows = followRepository.findByFromUserId(userId);
        Map result = new HashMap<String,Object>();
        List<Object> users = new ArrayList<>();
        for (Follow follow : follows) {
            Map userInfo = new HashMap<String,Object>();
            User findUser = userRepository.findById(follow.getToUserId()).get();
            userInfo.put("approve",follow.getApprove());
            userInfo.put("id",findUser.getId());
            userInfo.put("imageURL",findUser.getImageURL());
            userInfo.put("nickname",findUser.getNickname());
            users.add(userInfo);
        }
        result.put("users", users);
        return ResponseEntity.ok()
                .body(ResponseDto.success(result));
    }

    @PostMapping("/follow")
    public ResponseEntity<ResponseDto> saveFollow(@RequestBody Map<String,Long> param) {
        followRepository.save(Follow.builder()
                .fromUserId(param.get("senderId"))
                .toUserId(param.get("receiverId"))
                .approve(0)
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .build());

        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @PostMapping("/follow-approve")
    public ResponseEntity<ResponseDto> approveFollow(@RequestBody Map<String,Long> param) {
        Follow follow = followRepository.findByFromUserIdAndToUserId(param.get("senderId"), param.get("receiverId")).get();
        follow.updateApprove();
        followRepository.save(follow);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

    @DeleteMapping("/followings")
    public ResponseEntity<ResponseDto> deleteFollowing(@RequestBody Map<String,Long> param) {
        Follow follow = followRepository.findByFromUserIdAndToUserId(param.get("senderId"), param.get("receiverId")).get();
        followRepository.delete(follow);
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }

}
