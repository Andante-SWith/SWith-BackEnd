package com.andante.swith.repository;

import com.andante.swith.entity.Certification;
import com.andante.swith.entity.Statistic;
import com.andante.swith.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface StatisticRepository extends JpaRepository<Statistic,Long> {
    Optional<Statistic> findByUser(User user);

    Optional<Statistic> findByUserAndDate(User user, Date date);
}
