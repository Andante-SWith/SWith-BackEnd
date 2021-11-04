package com.andante.swith.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class StatisticDto {
    private Long userId;
    private Time studyTime;
    private Date date;
}
