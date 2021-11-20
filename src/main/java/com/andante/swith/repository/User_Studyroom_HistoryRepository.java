package com.andante.swith.repository;

import com.andante.swith.entity.Studyplanner;
import com.andante.swith.entity.User;
import com.andante.swith.entity.User_Studyroom_History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface User_Studyroom_HistoryRepository extends JpaRepository<User_Studyroom_History,Long> {
    List<User_Studyroom_History> findByUser(User user);
}
