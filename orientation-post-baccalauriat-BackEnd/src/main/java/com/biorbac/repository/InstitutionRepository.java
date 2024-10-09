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
    @Query("SELECT i FROM Institution i JOIN i.fieldOfStudies f ON WHERE " +
            "(f.minimumBacNote <= :bacScore) AND " +
            "(f.bacTypeRequired = :bacType)")
    List<Institution> findByCriteria(@Param("bacScore") double bacScore,
                                     @Param("bacType") BacType bacType);

    List<Institution> findByInstitutionTypeContainingIgnoreCaseOrInstitutionNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
            InstitutionType institutionType, String institutionName, String address, Sort sort);


}
