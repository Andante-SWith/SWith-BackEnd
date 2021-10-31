package com.andante.swith.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.sql.Timestamp;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Follow {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="from_user_id")
    private User from;

    @ManyToOne
    @JoinColumn(name="to_user_id")
    private User to;

    @NotNull
    @Column(nullable = false)
    private Timestamp createdDate;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("0")
    private Short approve;

    public Follow(User from, User to) {
        this.from = from;
        this.to = to;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }
}
