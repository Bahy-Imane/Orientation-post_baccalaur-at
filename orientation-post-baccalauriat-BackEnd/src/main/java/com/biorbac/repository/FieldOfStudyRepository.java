package com.biorbac.repository;

import com.biorbac.model.FieldOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {

//    @Query("SELECT fs FROM FieldOfStudy fs " +
//            "JOIN fs.department d " +
//            "JOIN d.institution i " +
//            "WHERE fs.bacTypeRequired = :bacTypeRequired")
//    List<FieldOfStudy> findByBacTypeRequiredWithInstitution(@Param("bacTypeRequired") String bacTypeRequired);

}
