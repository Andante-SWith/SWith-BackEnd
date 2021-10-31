package com.andante.swith.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Recommand {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "studyroom_id")
    private Studyroom studyroom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(nullable = false)
    private Timestamp createdDate;

}
