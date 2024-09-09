package com.biorbac.service;

import com.biorbac.dto.InstitutionDto;
import com.biorbac.enums.Interest;
import com.biorbac.mapper.InstitutionMapper;
import com.biorbac.model.Institution;
import com.biorbac.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class InstitutionService {
    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private InstitutionMapper institutionMapper;

    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }



    public InstitutionDto addInstitution(InstitutionDto institutionDto) {
        Institution institution = institutionMapper.toInstitutionDto(institutionDto);
    return institutionMapper.toInstitution(institutionRepository.save(institution));
    }



    public InstitutionDto updateInstitution(Long institutionId, InstitutionDto institutionDto) {
        if (institutionDto.getName() == null || institutionDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Institution name cannot be null or empty");
        }

        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        institutionMapper.updateInstitutionFromDto(institutionDto, institution);

        Institution updatedInstitution = institutionRepository.save(institution);

        return institutionMapper.toInstitution(updatedInstitution);
    }



    public List<Institution> findInstitution(String name, String subject, String ville, String description, String emailContact, Interest specialization) {
        return institutionRepository.findAll((root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (StringUtils.hasText(name)) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("name"), name));
            }
            if (StringUtils.hasText(subject)) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("subject"), subject));
            }
            if (StringUtils.hasText(ville)) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("ville"), ville));
            }
            if (StringUtils.hasText(description)) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("description"), "%" + description + "%"));
            }
            if (StringUtils.hasText(emailContact)) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("emailContact"), emailContact));
            }
            if (specialization != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("specialization"), specialization));
            }

            return predicates;
        });
    }

    public void deleteInstitution(Long institutionId) {
        institutionRepository.deleteById(institutionId);
    }
}
