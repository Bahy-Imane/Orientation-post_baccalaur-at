package com.biorbac.service;

import com.biorbac.dto.InstitutionDto;
import com.biorbac.enums.BacType;
import com.biorbac.enums.InstitutionType;
import com.biorbac.mapper.InstitutionMapper;
import com.biorbac.model.Institution;
import com.biorbac.model.Student;
import com.biorbac.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    private InstitutionMapper institutionMapper;


    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    public ResponseEntity<InstitutionDto> getInstitutionById(Long id) {
        Optional<Institution> institutionOpt = institutionRepository.findById(id);
        return institutionOpt.map(institution -> ResponseEntity.ok(institutionMapper.toInstitution(institution)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public InstitutionDto createInstitution(InstitutionDto institutionDto) {
        Institution institution = institutionMapper.toInstitutionDto(institutionDto);
        Institution savedInstitution = institutionRepository.save(institution);
        return institutionMapper.toInstitution(savedInstitution);
    }

    public ResponseEntity<InstitutionDto> updateInstitution(Long id, InstitutionDto institutionDto) {
        Optional<Institution> institutionOpt = institutionRepository.findById(id);
        if (institutionOpt.isPresent()) {
            Institution institution = institutionOpt.get();
            institutionMapper.updateInstitutionFromDto(institutionDto, institution);
            Institution updatedInstitution = institutionRepository.save(institution);
            return ResponseEntity.ok(institutionMapper.toInstitution(updatedInstitution));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Void> deleteInstitution(Long id) {
        if (institutionRepository.existsById(id)) {
            institutionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    public List<Institution> filterAndSortInstitutions(InstitutionType institutionType, String institutionName, String address) {
        return institutionRepository.findByInstitutionTypeContainingIgnoreCaseOrInstitutionNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
                institutionType, institutionName, address, Sort.by(Sort.Direction.ASC, "institutionName"));
    }
}
