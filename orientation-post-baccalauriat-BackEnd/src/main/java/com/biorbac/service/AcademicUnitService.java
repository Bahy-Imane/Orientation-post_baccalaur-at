package com.biorbac.service;

import com.biorbac.model.AcademicUnit;
import com.biorbac.repository.AcademicUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicUnitService {
    @Autowired
    private AcademicUnitRepository academicUnitRepository;

    public List<AcademicUnit> getAllAcademicUnits() {
        return academicUnitRepository.findAll();
    }

    public List<AcademicUnit> getAcademicByAcademicName(String academicName) {

    }

}
