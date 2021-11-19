package com.andante.swith.common.dto;

import com.andante.swith.entity.Recommand;
import com.andante.swith.entity.Report;
import com.andante.swith.entity.Studyroom_Hashtag;
import com.andante.swith.entity.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
public class StudyroomDto {

    private String title;

    private String purpose;

    private Short secret;

    private String password;

    private String notice;

    private String endDate;

    private Integer maxUserCount;

    private Long masterId;

    private List<String> hashtag = new ArrayList<>();
}
