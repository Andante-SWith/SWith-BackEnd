package com.andante.swith.repository;

import com.andante.swith.entity.Studyroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyroomRepository extends JpaRepository<Studyroom,Long> {
    List<Studyroom> findByMasterId(Long masterId);
}
