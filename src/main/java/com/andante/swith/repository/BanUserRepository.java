package com.andante.swith.repository;

import com.andante.swith.entity.BanUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanUserRepository extends JpaRepository<BanUser,Long> {
  
  List<BanUser> findByStudyroom(Studyroom studyroom);
}
