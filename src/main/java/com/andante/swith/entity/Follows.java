package com.andante.swith.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Timestamp;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Follows {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name="from_user_id")
    private User from;

    @ManyToOne
    @JoinColumn(name="to_user_id")
    private User to;

    private Timestamp createdDate;

    public Follows(User from, User to) {
        this.from = from;
        this.to = to;
    }

}
