package com.andante.swith.common.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudyplannerDto {
    private String taskDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Short complete;
}
