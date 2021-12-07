package com.andante.swith.repository;

import com.andante.swith.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    List<Follow> findByFromUserId(Long fromUserId);

    List<Follow> findByToUserId(Long toUserId);
}
