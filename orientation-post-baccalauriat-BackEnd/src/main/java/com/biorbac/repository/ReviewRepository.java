package com.biorbac.repository;

import com.biorbac.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
        List<Review> findByInstitution_InstitutionId(Long institutionId);

        List<Review> findByStudentUserName(String username);
}
