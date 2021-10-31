package com.andante.swith.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class EntityTest {

    @Autowired EntityManager em;
    User user1 = new User();
    User user2 = new User();
    Studyroom studyroom1 = new Studyroom();

    @Test
    public void saveMember() {
        user1.setStudyroom(studyroom1);
        user2.setStudyroom(studyroom1);

        em.persist(user1);
        em.persist(user2);
        em.persist(studyroom1);
        studyroom1.getUsers().add(user2);
        user1.setUserStudyroomHistory(studyroom1);
//        em.remove(user1);

    }
//    @Test
//    public void saveStudyroom() {
//
//    }
//    @Test
//    public void saveMemeber_Studyroom_History() {
//
//    }
}