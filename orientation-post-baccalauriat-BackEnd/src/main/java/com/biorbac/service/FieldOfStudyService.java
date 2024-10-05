package com.biorbac.service;


import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Institution;
import com.biorbac.model.Student;
import com.biorbac.repository.FieldOfStudyRepository;
import com.biorbac.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldOfStudyService {

    @Autowired
    public FieldOfStudyRepository fieldOfStudyRepository;

    @Autowired
    public FieldOfStudyMapper fieldOfStudyMapper;

    @Autowired
    public InstitutionRepository institutionRepository;


    public List<FieldOfStudy> getAllFieldOfStudy() {
        return fieldOfStudyRepository.findAll();
    }


//        public FieldOfStudyDto addFieldOfStudy(Institution institution, FieldOfStudyDto fieldOfStudyDto) {
//
//        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudyDto);
//        fieldOfStudy.setInstitution(institution);
//
//        FieldOfStudy savedFieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);
//
//        return fieldOfStudyMapper.toFieldOfStudy(savedFieldOfStudy);
//    }

    public FieldOfStudyDto addFieldOfStudy(Long institutionId, FieldOfStudyDto fieldOfStudyDto) {
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudyDto);
        fieldOfStudy.setInstitution(institution);

        FieldOfStudy savedFieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);

        return fieldOfStudyMapper.toFieldOfStudy(savedFieldOfStudy);
    }

//    public FieldOfStudyDto addFieldOfStudy(FieldOfStudyDto fieldOfStudyDto) {
//        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudyDto);
//
//        if (fieldOfStudyDto.getInstitution() != null) {
//            Institution institution = institutionRepository.findById(fieldOfStudyDto.getInstitution().getInstitutionId())
//                    .orElseThrow(() -> new RuntimeException("Institution non trouvée avec l'ID : " + fieldOfStudyDto.getInstitution().getInstitutionId()));
//            fieldOfStudy.setInstitution(institution);
//        }
//        FieldOfStudy savedFieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);
//        return fieldOfStudyMapper.toFieldOfStudy(savedFieldOfStudy);
//    }


    public FieldOfStudyDto updateFieldOfStudy(Long fosId, FieldOfStudyDto fieldOfStudyDto) {
        if (fieldOfStudyDto.getName() == null || fieldOfStudyDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Field Of Study unit name cannot be null or empty");
        }

        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(fosId).orElseThrow(() -> new RuntimeException("Field Of Study not found"));

        fieldOfStudyMapper.updateFieldOfStudyFromDto(fieldOfStudyDto, fieldOfStudy);

        FieldOfStudy updatedFieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);

        return fieldOfStudyMapper.toFieldOfStudy(updatedFieldOfStudy);
    }


    public void deleteFieldOfStudy(Long fieldOfStudyId) {
        fieldOfStudyRepository.deleteById(fieldOfStudyId);
    }

}
//    public List<FieldOfStudy> recommend(Student student) {
//        double bacScore = student.getBacScore();
//        String bacType = student.getBacType().name();
//
//        System.out.println("Bac Score: " + bacScore);
//        System.out.println("Bac Type: " + bacType);
//
//        List<FieldOfStudy> fields = fieldOfStudyRepository.findByBacTypeRequiredWithInstitution(bacType);
//        System.out.println("Fields Found: " + fields.size());
//
//        List<FieldOfStudy> recommendations = fields.stream()
//                .filter(field -> bacScore >= field.getMinimumBacNote())
//                .collect(Collectors.toList());
//
//        System.out.println("Recommendations Count: " + recommendations.size());
//        return recommendations;
//    }




