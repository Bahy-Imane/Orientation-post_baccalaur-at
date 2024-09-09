package com.biorbac.service;


import com.biorbac.dto.AcademicUnitDto;
import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.AcademicUnit;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Institution;
import com.biorbac.repository.AcademicUnitRepository;
import com.biorbac.repository.FieldOfStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldOfStudyService {

    @Autowired
    private FieldOfStudyRepository fieldOfStudyRepository;

    @Autowired
    private FieldOfStudyMapper fieldOfStudyMapper;

    @Autowired
    private AcademicUnitRepository academicUnitRepository;


    public List<FieldOfStudy> getAllFieldOfStudy() {
        return fieldOfStudyRepository.findAll();
    }


    public FieldOfStudyDto AddFieldOfStudy(Long academicId, FieldOfStudyDto fieldOfStudyDto) {
        AcademicUnit academicUnit = academicUnitRepository.findById(academicId).orElseThrow();
        academicUnit.setAcademicId(academicId);
        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudyDto);
        return fieldOfStudyMapper.toFieldOfStudy(fieldOfStudyRepository.save(fieldOfStudy));
    }


    public FieldOfStudyDto updateFieldOfStudy(Long fosId,FieldOfStudyDto fieldOfStudyDto) {
        if(fieldOfStudyDto.getName()==null || fieldOfStudyDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Field Of Study unit name cannot be null or empty");
        }

        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(fosId).orElseThrow(()->new RuntimeException("Field Of Study not found"));

        fieldOfStudyMapper.updateFieldOfStudyFromDto(fieldOfStudyDto, fieldOfStudy );

        FieldOfStudy updatedFieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);

        return fieldOfStudyMapper.toFieldOfStudy(updatedFieldOfStudy);
    }

    

    public void deleteFieldOfStudy( Long fieldOfStudyId) {
        fieldOfStudyRepository.deleteById(fieldOfStudyId);
    }

}
