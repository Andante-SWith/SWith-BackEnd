package com.andante.swith.repository;

import com.andante.swith.entity.Statistic;
import com.andante.swith.entity.Studyplanner;
import com.andante.swith.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyplannerRepository extends JpaRepository<Studyplanner,Long> {
    Optional<Studyplanner> findByUser(User user);
}
