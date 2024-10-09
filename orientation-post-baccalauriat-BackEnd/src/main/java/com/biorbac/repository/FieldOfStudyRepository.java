package com.biorbac.repository;

import com.biorbac.enums.BacType;
import com.biorbac.model.FieldOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {
    List<FieldOfStudy> findByMinimumBacNoteLessThanEqualAndBacTypeRequired(double bacScore, BacType bacType);
}
