package com.andante.swith.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
