package com.andante.swith.repository;

import com.andante.swith.entity.BanUser;
import com.andante.swith.entity.Studyroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BanUserRepository extends JpaRepository<BanUser,Long> {
  
  List<BanUser> findByStudyroom(Studyroom studyroom);
}
