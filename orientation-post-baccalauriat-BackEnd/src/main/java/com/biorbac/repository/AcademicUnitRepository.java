package com.biorbac.repository;

import com.biorbac.model.AcademicUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicUnitRepository extends JpaRepository<AcademicUnit, Long> {
    AcademicUnit findAcademicUnitByUnitName(String name);
}
