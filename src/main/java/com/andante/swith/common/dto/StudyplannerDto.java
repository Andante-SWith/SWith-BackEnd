package com.andante.swith.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StudyplannerDto {
    private String taskDescription;
    private Timestamp date;
    private Short complete;
}
