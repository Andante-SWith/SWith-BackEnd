package com.andante.swith.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "studyroom_id")
    private Studyroom studyroom;

    @NotNull
    @Column(nullable = false)
    private Timestamp createdDate;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("0")
    private Short deleted;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("0")
    private Short banned;

    private Timestamp ban_date;

    @OneToOne(mappedBy = "user")
    private Studyplanner studyplanner;

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<User_Studyroom_History> user_studyroom_historys = new ArrayList<>();

    @OneToMany(mappedBy="to", cascade = ALL, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy="from", cascade = ALL, orphanRemoval = true)
    private List<Follow> following = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade = ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade = ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy="to", cascade = ALL, orphanRemoval = true)
    private List<Notice> toNotices = new ArrayList<>();

    @OneToMany(mappedBy="from", cascade = ALL, orphanRemoval = true)
    private List<Notice> fromNotices = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade = ALL, orphanRemoval = true)
    private List<Statistic> statistics = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade = ALL, orphanRemoval = true)
    private List<Recommand> recommands = new ArrayList<>();

    @OneToMany(mappedBy="reportingUser", cascade = ALL, orphanRemoval = true)
    private List<Report> reportings = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade = ALL, orphanRemoval = true)
    private List<Report> reporteds = new ArrayList<>();

    public void setUserStudyroomHistory(Studyroom studyroom) {
        User_Studyroom_History user_studyroom_history = new User_Studyroom_History(this, studyroom);
        getUser_studyroom_historys().add(user_studyroom_history);
    }

    public void setStudyroom(Studyroom studyroom) {
        this.studyroom = studyroom;
        studyroom.getUsers().add(this);
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
