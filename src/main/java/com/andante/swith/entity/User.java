package com.andante.swith.entity;

import lombok.Getter;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String password;
    private String email;
    private String nickname;
//    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "studyroom_id")
//    private Studyroom studyroom;
    private Timestamp createdDate;
    private Integer deleted;
    private Integer reportCount;
    private Timestamp ban_date;
    private Integer ban_status;

}
