package com.andante.swith.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private Timestamp createdDate;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy="board", cascade = ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy="board", cascade = ALL, orphanRemoval = true)
    private List<Report> reporteds = new ArrayList<>();

}
