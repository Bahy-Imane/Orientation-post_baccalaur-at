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

    List<Institution> findByInstitutionTypeContainingIgnoreCaseOrInstitutionNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
            InstitutionType institutionType, String institutionName, String address, Sort sort);


}
