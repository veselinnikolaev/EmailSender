package org.example.emailsender.repository;

import jakarta.transaction.Transactional;
import org.example.emailsender.entity.Influencer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    @Transactional
    void deleteByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    void deleteByCourseName(String courseName);
}
