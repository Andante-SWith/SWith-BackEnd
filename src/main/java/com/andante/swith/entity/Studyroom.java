package com.andante.swith.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Studyroom {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "studyroom")
    private List<User> users = new ArrayList<>();

    private String title;
    private String purpose;
    private Timestamp createdDate;
    private Integer type;
    private String password;
    private String notice;
    private Timestamp endDate;
    private Integer maxUserCount;
    private Integer userCount;
    private Integer reportCount;


}
