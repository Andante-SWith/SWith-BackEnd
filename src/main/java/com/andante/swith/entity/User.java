package com.andante.swith.entity;

import lombok.*;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String password;
    private String email;
    private String nickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "studyroom_id")
    private Studyroom studyroom;

    //추가 -> 객체지향적으로 .add하려면 양방향 설정을 해줘야함. ***중요!! DB단에서는 외래키로 연관관계가 무조건 양방향으로 설정되지만 객체는 단방향 두개로 설정된 것이므로 객체지향적으로 접근하려면 무조건 양방향으로 하는게 맞음.
    // 근데 단방향으로 oneToMany 설정을 해줘도 되지만, 먼저 김영한님은 일대다 단방향 매핑은 이러한 단점이 있다고 했음.
    //
    //엔티티가 관리하는 외래 키가 다른 테이블에 있음 (Many에 외래키 존재)
    //연관관계 관리를 위해 추가로 update sql 실행 (성능상 큰 차이는 없다)
    //개발을 하다 보면 B를 만졌는데 A도 update sql문이 나가니 헷갈린다.
    //그래서 필요하다면 일대다 보다는 양방향 관계로 한다. ( B는 A가 필요 없더라도, 객체 지향적으로 손해를 보는 거 같지만) - 트레이드 오프
    //orphanRemoval-> 자식을 수정하면 새로운 자식 insert 후 update 기존의 자식은 null로 update한다. orphanRemoval = true는 null로된 자식을 자동으로 delete해준다.

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<User_Studyroom_History> user_studyroom_historys = new ArrayList<>();

    //https://stackoverflow.com/questions/44331694/following-followers-in-spring
    @OneToMany(mappedBy="to")
    private List<Follows> followers;

    @OneToMany(mappedBy="from")
    private List<Follows> following;

    private Timestamp createdDate;

    private Integer deleted;
    private Integer reportCount;
    private Timestamp ban_date;
    private Integer ban_status;


//    public User(String password, String email, String nickname, Studyroom studyroom, Timestamp createdDate, Integer deleted, Integer reportCount, Timestamp ban_date, Integer ban_status) {
//        this.password = password;
//        this.email = email;
//        this.nickname = nickname;
//        this.studyroom = studyroom;
//        this.createdDate = createdDate;
//        this.deleted = deleted;
//        this.reportCount = reportCount;
//        this.ban_date = ban_date;
//        this.ban_status = ban_status;
//    }

    public void setUserStudyroomHistory(Studyroom studyroom) {
        User_Studyroom_History user_studyroom_history = new User_Studyroom_History(this, studyroom);
        getUser_studyroom_historys().add(user_studyroom_history);
    }

    public void setStudyroom(Studyroom studyroom) {
        this.studyroom = studyroom;
        studyroom.getUsers().add(this);
    }
}
