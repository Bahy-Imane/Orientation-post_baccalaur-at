package com.biorbac.service;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.enums.BacType;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Institution;
import com.biorbac.model.Student;
import com.biorbac.repository.FieldOfStudyRepository;
import com.biorbac.repository.InstitutionRepository;
import com.biorbac.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public List<FieldOfStudy> getAllFieldOfStudy() {
        return fieldOfStudyRepository.findAll();
    }

    public ResponseEntity<FieldOfStudyDto> getFieldOfStudyById(Long id) {
        Optional<FieldOfStudy> fieldOfStudyOpt = fieldOfStudyRepository.findById(id);
        return fieldOfStudyOpt.map(fieldOfStudy -> ResponseEntity.ok(fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudy)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public FieldOfStudyDto addFieldOfStudy(FieldOfStudyDto fieldOfStudyDto) {
        Institution institution = institutionRepository.findById(fieldOfStudyDto.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toFieldOfStudy(fieldOfStudyDto);
        fieldOfStudy.setInstitution(institution);

        FieldOfStudy savedFieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);

        return fieldOfStudyMapper.toFieldOfStudyDto(savedFieldOfStudy);
    }

    public FieldOfStudyDto updateFieldOfStudy(Long fosId, FieldOfStudyDto fieldOfStudyDto) {
        if (fieldOfStudyDto.getName() == null || fieldOfStudyDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Field Of Study name cannot be null or empty");
        }

        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(fosId)
                .orElseThrow(() -> new RuntimeException("Field Of Study not found"));

        fieldOfStudyMapper.updateFieldOfStudyFromDto(fieldOfStudyDto, fieldOfStudy);

        FieldOfStudy updatedFieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);

        return fieldOfStudyMapper.toFieldOfStudyDto(updatedFieldOfStudy);
    }

    public void deleteFieldOfStudy(Long fieldOfStudyId) {
        fieldOfStudyRepository.deleteById(fieldOfStudyId);
    }


    public List<FieldOfStudy> recommendBasedOnStudent() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Student student1 = studentRepository.findStudentByUserName(loggedInUser.getName());
        return fieldOfStudyRepository.findByMinimumBacNoteLessThanEqualAndBacTypeRequired(student1.getBacScore(), student1.getBacType());
    }


    public List<FieldOfStudyDto> filterAndSearchFields(BacType bacTypeRequired, Double minimumBacNote, String searchText) {
        List<FieldOfStudy> fieldsOfStudy = fieldOfStudyRepository.filterAndSearch(bacTypeRequired, minimumBacNote, searchText);
        return fieldsOfStudy.stream()
                .map(fieldOfStudyMapper::toFieldOfStudyDto)
                .collect(Collectors.toList());
    }
}
