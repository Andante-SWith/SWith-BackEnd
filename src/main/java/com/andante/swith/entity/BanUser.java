package com.andante.swith.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class BanUser {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "studyroom_id")
    private Studyroom studyroom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BanUser(User user, Studyroom studyroom) {
        this.studyroom = studyroom;
        this.user = user;
    }
}
