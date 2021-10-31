package com.andante.swith.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_user_id")
    private User from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_user_id")
    private User to;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "studyroom_id")
    private Studyroom studyroom;

    //팔로잉 알림 추가하기

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("0")
    private Short checked;

    @NotNull
    @Column(nullable = false)
    private Timestamp createdDate;

}
