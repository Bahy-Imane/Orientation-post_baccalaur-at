package com.biorbac.repository;

import com.biorbac.enums.BacType;
import com.biorbac.enums.InstitutionType;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Institution;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {
    List<FieldOfStudy> findByMinimumBacNoteLessThanEqualAndBacTypeRequired(double bacScore, BacType bacType);

    List<FieldOfStudy> findFieldOfStudiesByInstitution_InstitutionId(Long institutionId);

    List<FieldOfStudy> findByBacTypeRequired(BacType bacTypeRequired);

    @Query("SELECT f FROM FieldOfStudy f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(f.departmentName) LIKE LOWER(CONCAT('%', :searchText, '%')) ")
    List<FieldOfStudy> searchByText(String searchText);
}
