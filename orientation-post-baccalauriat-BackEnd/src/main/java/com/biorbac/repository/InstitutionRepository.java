package com.biorbac.repository;

import com.biorbac.enums.BacType;
import com.biorbac.enums.InstitutionType;
import com.biorbac.model.Institution;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long>, JpaSpecificationExecutor<Institution> {

    @Query("SELECT i FROM Institution i WHERE LOWER(i.institutionName) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(i.description) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(i.address) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Institution> searchByText(@Param("searchText") String searchText);

    List<Institution> findByInstitutionType(InstitutionType institutionType);

    List<Institution> findByAverageRatingGreaterThanEqual(Double minRating);
}
