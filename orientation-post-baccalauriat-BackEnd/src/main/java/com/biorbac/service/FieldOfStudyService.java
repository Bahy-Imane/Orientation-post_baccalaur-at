package com.biorbac.service;


import com.biorbac.dto.DepartmentDto;
import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.Department;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Institution;
import com.biorbac.repository.DepartmentRepository;
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
    private DepartmentRepository departmentRepository;


    public List<FieldOfStudy> getAllFieldOfStudy() {
        return fieldOfStudyRepository.findAll();
    }


    public FieldOfStudyDto AddFieldOfStudy(Long departmentId, FieldOfStudyDto fieldOfStudyDto) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + departmentId));

            FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudyDto);
            fieldOfStudy.setDepartment(department);
            FieldOfStudy savedFieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);
            return fieldOfStudyMapper.toFieldOfStudy(savedFieldOfStudy);
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
