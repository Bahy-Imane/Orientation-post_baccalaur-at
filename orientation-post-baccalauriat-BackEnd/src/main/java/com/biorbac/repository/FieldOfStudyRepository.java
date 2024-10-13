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

    @Query("SELECT f FROM FieldOfStudy f WHERE " +
            "(:bacTypeRequired IS NULL OR f.bacTypeRequired = :bacTypeRequired) OR " +
            "(:minimumBacNote IS NULL OR f.minimumBacNote <= :minimumBacNote) OR " +
            "(LOWER(f.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(f.departmentName) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    List<FieldOfStudy> filterAndSearch(@Param("bacTypeRequired") BacType bacTypeRequired,
                                       @Param("minimumBacNote") Double minimumBacNote,
                                       @Param("searchText") String searchText);
}
