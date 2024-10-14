package com.biorbac.service;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.dto.ReviewDto;
import com.biorbac.enums.BacType;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Institution;
import com.biorbac.model.Review;
import com.biorbac.model.Student;
import com.biorbac.repository.FieldOfStudyRepository;
import com.biorbac.repository.InstitutionRepository;
import com.biorbac.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FieldOfStudyService {

    @Autowired
    private FieldOfStudyRepository fieldOfStudyRepository;

    @Autowired
    private FieldOfStudyMapper fieldOfStudyMapper;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private StudentRepository studentRepository;


    public List<FieldOfStudyDto> getAllFieldOfStudy() {
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        return fieldOfStudyList.stream().map(fos -> {
            FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
            fieldOfStudyDto.setFosId(fos.getFosId());
            fieldOfStudyDto.setName(fos.getName());
            fieldOfStudyDto.setBacTypeRequired(fos.getBacTypeRequired().name());
            fieldOfStudyDto.setMinimumBacNote(fos.getMinimumBacNote());
            fieldOfStudyDto.setFieldOfStudyLogo(fos.getFieldOfStudyLogo());
            fieldOfStudyDto.setInstitutionName(fos.getInstitution().getInstitutionName());
            fieldOfStudyDto.setDepartmentName(fos.getDepartmentName());
            fieldOfStudyDto.setInstitutionId(fos.getInstitution().getInstitutionId());

            return fieldOfStudyDto;
        }).collect(Collectors.toList());
    }

    public List<FieldOfStudyDto> getFieldOfStudyById(Long fosId) {
        Optional<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findById(fosId);
        return fieldOfStudyList.stream().map(fos -> {
            FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
            fieldOfStudyDto.setFosId(fos.getFosId());
            fieldOfStudyDto.setName(fos.getName());
            fieldOfStudyDto.setBacTypeRequired(fos.getBacTypeRequired().name());
            fieldOfStudyDto.setMinimumBacNote(fos.getMinimumBacNote());
            fieldOfStudyDto.setFieldOfStudyLogo(fos.getFieldOfStudyLogo());
            fieldOfStudyDto.setInstitutionName(fos.getInstitution().getInstitutionName());
            fieldOfStudyDto.setDepartmentName(fos.getDepartmentName());
            fieldOfStudyDto.setInstitutionId(fos.getInstitution().getInstitutionId());

            return fieldOfStudyDto;
        }).collect(Collectors.toList());
    }

    public List<FieldOfStudyDto> getFieldOfStudiesByInstitutionId(Long institutionId) {
        List<FieldOfStudy> fieldOfStudyList =  fieldOfStudyRepository.findFieldOfStudiesByInstitution_InstitutionId(institutionId);
        return fieldOfStudyList.stream().map(fos -> {
            FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
            fieldOfStudyDto.setFosId(fos.getFosId());
            fieldOfStudyDto.setName(fos.getName());
            fieldOfStudyDto.setBacTypeRequired(fos.getBacTypeRequired().name());
            fieldOfStudyDto.setMinimumBacNote(fos.getMinimumBacNote());
            fieldOfStudyDto.setFieldOfStudyLogo(fos.getFieldOfStudyLogo());
            fieldOfStudyDto.setInstitutionName(fos.getInstitution().getInstitutionName());
            fieldOfStudyDto.setDepartmentName(fos.getDepartmentName());
            fieldOfStudyDto.setInstitutionId(fos.getInstitution().getInstitutionId());

            return fieldOfStudyDto;
        }).collect(Collectors.toList());
    }


    public Map<String, String> addFieldOfStudy(FieldOfStudyDto fieldOfStudyDto) {
        Institution institution = institutionRepository.findById(fieldOfStudyDto.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setInstitution(institution);
        fieldOfStudy.setName(fieldOfStudyDto.getName());
        fieldOfStudy.setBacTypeRequired(BacType.valueOf(fieldOfStudyDto.getBacTypeRequired()));
        fieldOfStudy.setMinimumBacNote(fieldOfStudyDto.getMinimumBacNote());
        fieldOfStudy.setFieldOfStudyLogo(fieldOfStudyDto.getFieldOfStudyLogo());
        fieldOfStudy.setDepartmentName(fieldOfStudyDto.getDepartmentName());

        fieldOfStudyRepository.save(fieldOfStudy);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("msg", "Field of study created");

        return responseMap;
    }


    public Map<String, String>  updateFieldOfStudy(Long fosId, FieldOfStudyDto fieldOfStudyDto) {
        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(fosId)
                .orElseThrow(() -> new RuntimeException("Field Of Study not found"));

        fieldOfStudy.setInstitution(fieldOfStudy.getInstitution());
        fieldOfStudy.setName(fieldOfStudyDto.getName());
        fieldOfStudy.setBacTypeRequired(BacType.valueOf(fieldOfStudyDto.getBacTypeRequired()));
        fieldOfStudy.setMinimumBacNote(fieldOfStudyDto.getMinimumBacNote());
        fieldOfStudy.setFieldOfStudyLogo(fieldOfStudyDto.getFieldOfStudyLogo());
        fieldOfStudy.setDepartmentName(fieldOfStudyDto.getDepartmentName());

        fieldOfStudyRepository.save(fieldOfStudy);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Field of study created");

        return  response;
    }

    public void deleteFieldOfStudy(Long fieldOfStudyId) {
        fieldOfStudyRepository.deleteById(fieldOfStudyId);
    }


    public List<FieldOfStudyDto> recommendBasedOnStudent() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Student student1 = studentRepository.findStudentByUserName(loggedInUser.getName());

        if (student1 == null) {
            throw new IllegalArgumentException("Student not found for the current authenticated user.");
        }
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findByMinimumBacNoteLessThanEqualAndBacTypeRequired(student1.getBacScore(), student1.getBacType());
        return fieldOfStudyList.stream().map(fos -> {
            FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
            fieldOfStudyDto.setFosId(fos.getFosId());
            fieldOfStudyDto.setName(fos.getName());
            fieldOfStudyDto.setBacTypeRequired(fos.getBacTypeRequired().name());
            fieldOfStudyDto.setMinimumBacNote(fos.getMinimumBacNote());
            fieldOfStudyDto.setFieldOfStudyLogo(fos.getFieldOfStudyLogo());
            fieldOfStudyDto.setInstitutionName(fos.getInstitution().getInstitutionName());
            fieldOfStudyDto.setDepartmentName(fos.getDepartmentName());
            fieldOfStudyDto.setInstitutionId(fos.getInstitution().getInstitutionId());

            return fieldOfStudyDto;
        }).collect(Collectors.toList());
    }



    public List<FieldOfStudyDto> searchByText(String searchText) {
        List<FieldOfStudy> fieldsOfStudy = fieldOfStudyRepository.searchByText(searchText);
        return fieldsOfStudy.stream()
                .map(fieldOfStudyMapper::toFieldOfStudyDto)
                .collect(Collectors.toList());
    }

    public List<FieldOfStudyDto> filterByBacType(String bacTypeRequired) {
        List<FieldOfStudy> fieldsOfStudy;

        if (bacTypeRequired == null || bacTypeRequired.isEmpty()) {
            fieldsOfStudy = fieldOfStudyRepository.findAll();
        } else {
            BacType bacType = BacType.valueOf(bacTypeRequired.toUpperCase());
            fieldsOfStudy = fieldOfStudyRepository.findByBacTypeRequired(bacType);
        }

        return fieldsOfStudy.stream()
                .map(fieldOfStudyMapper::toFieldOfStudyDto)
                .collect(Collectors.toList());
    }
}
