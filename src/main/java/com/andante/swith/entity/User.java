package com.andante.swith.entity;

import lombok.*;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String password;
    private String email;
    private String nickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "studyroom_id")
    private Studyroom studyroom;

    private Timestamp createdDate;

    private Integer deleted;
    private Integer reportCount;
    private Timestamp ban_date;
    private Integer ban_status;


    public User(String password, String email, String nickname, Studyroom studyroom, Timestamp createdDate, Integer deleted, Integer reportCount, Timestamp ban_date, Integer ban_status) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.studyroom = studyroom;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.reportCount = reportCount;
        this.ban_date = ban_date;
        this.ban_status = ban_status;
    }

    public void setStudyroom(Studyroom studyroom) {
        this.studyroom = studyroom;
        studyroom.getUsers().add(this);
    }
}
