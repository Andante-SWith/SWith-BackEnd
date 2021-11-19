package com.andante.swith.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Studyroom {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(length = 10, nullable = false)
    private String purpose;

    @NotNull
    @Column(nullable = false)
    private Timestamp createdDate;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("0")
    private Short secret;

    private String password;

    @NotNull
    @Column(nullable = false)
    private String notice;

    @NotNull
    @Column(nullable = false)
    private Timestamp endDate;

    @NotNull
    @Column(nullable = false)
    private Integer maxUserCount;

    @NotNull
    @Column(name = "master_id")
    private Long masterId;

    @Builder.Default
    @OneToMany(mappedBy = "studyroom")
    private List<User> users = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "studyroom", cascade = ALL, orphanRemoval = true)
    private List<Studyroom_Hashtag> hashtags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "studyroom", cascade = ALL, orphanRemoval = true)
    private List<Recommand> recommands = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy="studyroom", cascade = ALL, orphanRemoval = true)
    private List<Report> reporteds = new ArrayList<>();

    public void createHashtag(){
        this.hashtags = new ArrayList<>();
    }

    public void updateStudyroom(String title, String purpose, Short secret, String password, String notice, Timestamp endDate, Integer maxUserCount) {
        this.title = title;
        this.purpose = purpose;
        this.secret = secret;
        this.password = password;
        this.notice = notice;
        this.endDate = endDate;
        this.maxUserCount = maxUserCount;
    }

}
