package com.andante.swith.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Time;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Statistic {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(nullable = false)
    private Time studyTime;

    @NotNull
    @Column(nullable = false)
    private Date date;

    public void addStudyTime(Time studyTime) {
        int hour=0, minute=0, second=0;
        hour=Integer.parseInt(this.studyTime.toString().substring(0,2))+Integer.parseInt(studyTime.toString().substring(0,2));
        minute=Integer.parseInt(this.studyTime.toString().substring(3,5))+Integer.parseInt(studyTime.toString().substring(3,5));
        second=Integer.parseInt(this.studyTime.toString().substring(6,8))+Integer.parseInt(studyTime.toString().substring(6,8));
        this.studyTime=Time.valueOf(hour+":"+minute+":"+second);
    }
}
