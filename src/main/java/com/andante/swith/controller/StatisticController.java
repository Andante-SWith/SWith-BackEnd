package com.andante.swith.controller;

import com.andante.swith.common.dto.ResponseDto;
import com.andante.swith.common.dto.StatisticDto;
import com.andante.swith.entity.Statistic;
import com.andante.swith.repository.StatisticRepository;
import com.andante.swith.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StatisticController {

    private final UserRepository userRepository;
    private final StatisticRepository statisticRepository;

    @GetMapping("/statistics/{user_id}")
    public ResponseEntity<ResponseDto> getStatistics(@PathVariable("user_id") Long userId) {
        Statistic statistic = statisticRepository.findByUser(userRepository.findById(userId).get()).get();
        return ResponseEntity.ok()
                .body(ResponseDto.success(statistic));
    }
    @PostMapping("/statistics")
    public ResponseEntity<ResponseDto> saveStatistics(@RequestBody StatisticDto statistic) {
        Optional<Statistic> findStatistic = statisticRepository.findByUserAndDate(userRepository.findById(statistic.getUserId()).get(), statistic.getDate());
        if(findStatistic.isPresent()==false) {
            statisticRepository.save(Statistic.builder()
                    .user(userRepository.findById(statistic.getUserId()).get())
                    .studyTime(statistic.getStudyTime())
                    .date(statistic.getDate())
                    .build());
        }
        else {
            findStatistic.get().addStudyTime(statistic.getStudyTime());
            statisticRepository.save(findStatistic.get());
        }
        return ResponseEntity.ok()
                .body(ResponseDto.success(null));
    }
}
