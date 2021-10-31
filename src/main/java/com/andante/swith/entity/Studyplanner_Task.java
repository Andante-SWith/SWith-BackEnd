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
public class Studyplanner_Task {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "studyplanner_id")
    private Studyplanner studyplanner;

    @NotNull
    @Column(nullable = false)
    private String taskTitle;

    @NotNull
    @Column(nullable = false)
    private String taskDescription;

    @NotNull
    @Column(nullable = false)
    private Timestamp startDate;

    @NotNull
    @Column(nullable = false)
    private Timestamp endDate;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("0")
    private Short complete;

    @NotNull
    @Column(nullable = false)
    private String color;
}
