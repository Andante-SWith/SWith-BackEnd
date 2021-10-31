package com.andante.swith.repository;

import com.andante.swith.entity.Certification;
import com.andante.swith.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification,Long> {
    Optional<Certification> findByEmail(String email);
}
