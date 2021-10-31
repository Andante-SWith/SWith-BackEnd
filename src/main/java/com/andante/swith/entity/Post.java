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
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(length = 1000, nullable = false)
    private String contents;

    @NotNull
    @Column(nullable = false)
    private Timestamp createdDate;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer viewCount;

    @OneToMany(mappedBy="post", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy="post", cascade = ALL, orphanRemoval = true)
    private List<Recommand> recommands = new ArrayList<>();

    @OneToMany(mappedBy="post", cascade = ALL, orphanRemoval = true)
    private List<Report> reporteds = new ArrayList<>();
}