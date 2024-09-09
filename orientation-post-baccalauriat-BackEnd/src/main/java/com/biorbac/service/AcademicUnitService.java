package com.biorbac.service;

import com.biorbac.dto.AcademicUnitDto;
import com.biorbac.dto.InstitutionDto;
import com.biorbac.mapper.AcademicMapper;
import com.biorbac.model.AcademicUnit;
import com.biorbac.model.Institution;
import com.biorbac.repository.AcademicUnitRepository;
import com.biorbac.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicUnitService {
    @Autowired
    private AcademicUnitRepository academicUnitRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private AcademicMapper academicMapper;

    public List<AcademicUnit> getAllAcademicUnits() {
        return academicUnitRepository.findAll();
    }

    public AcademicUnit getAcademicUnitById(Long id) {
        return academicUnitRepository.findById(id).orElseThrow();
    }


    public AcademicUnitDto addAcademicUnit(Long institutionId,AcademicUnitDto academicUnitDto) {
        Institution institution = institutionRepository.findById(institutionId).orElseThrow();
        institution.setInstitutionId(institutionId);
        AcademicUnit academicUnit = academicMapper.toAcademicUnitDto(academicUnitDto);
        return academicMapper.toAcademicUnit(academicUnitRepository.save(academicUnit));
    }



    public AcademicUnitDto updateAcademicUnit(Long academicId,AcademicUnitDto academicUnitDto) {
        if(academicUnitDto.getUnitName()==null || academicUnitDto.getUnitName().isEmpty()) {
            throw new IllegalArgumentException("Academic unit name cannot be null or empty");
        }

        AcademicUnit academicUnit = academicUnitRepository.findById(academicId).orElseThrow(()->new RuntimeException("Academic unit not found"));

        academicMapper.updateAcademicUnitFromDto(academicUnitDto, academicUnit );

        AcademicUnit updatedAcademic = academicUnitRepository.save(academicUnit);

        return academicMapper.toAcademicUnit(updatedAcademic);
    }


    public void deleteAcademicUnit(Long academicUnitId) {
        academicUnitRepository.deleteById(academicUnitId);
    }






}
