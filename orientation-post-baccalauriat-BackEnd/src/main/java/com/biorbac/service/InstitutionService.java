package com.biorbac.service;

import com.biorbac.dto.InstitutionDto;
import com.biorbac.enums.BacType;
import com.biorbac.enums.InstitutionType;
import com.biorbac.mapper.InstitutionMapper;
import com.biorbac.model.Institution;
import com.biorbac.model.Review;
import com.biorbac.model.Student;
import com.biorbac.repository.InstitutionRepository;
import com.biorbac.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private InstitutionMapper institutionMapper;

    public List<InstitutionDto> getAllInstitutions() {
        return institutionRepository.findAll()
                .stream()
                .map(this::convertToInstitutionDto)
                .collect(Collectors.toList());
    }

    public InstitutionDto getInstitutionById(Long institutionId) {
        return institutionRepository.findById(institutionId)
                .map(this::convertToInstitutionDto)
                .orElseThrow(() -> new RuntimeException("Institution not found"));
    }

    private InstitutionDto convertToInstitutionDto(Institution institution) {
        double averageRating = calculateAverageRating(institution.getInstitutionId());
        InstitutionDto dto = institutionMapper.toInstitutionDto(institution);
        dto.setAverageRating(averageRating);
        return dto;
    }

    public double calculateAverageRating(Long institutionId) {
        List<Review> reviews = reviewRepository.findByInstitution_InstitutionId(institutionId);
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public InstitutionDto createInstitution(InstitutionDto institutionDto) {
        Institution institution = institutionMapper.toInstitution(institutionDto);
        Institution savedInstitution = institutionRepository.save(institution);
        return institutionMapper.toInstitutionDto(savedInstitution);
    }



//    public InstitutionDto createInstitution(InstitutionDto institutionDto) {
//        Institution institution = institutionMapper.toInstitutionDto(institutionDto);
//        Institution savedInstitution = institutionRepository.save(institution);
//        return institutionMapper.toInstitution(savedInstitution);
//    }

    public ResponseEntity<InstitutionDto> updateInstitution(Long id, InstitutionDto institutionDto) {
        Optional<Institution> institutionOpt = institutionRepository.findById(id);
        if (institutionOpt.isPresent()) {
            Institution institution = institutionOpt.get();
            institutionMapper.updateInstitutionFromDto(institutionDto, institution);
            Institution updatedInstitution = institutionRepository.save(institution);
            return ResponseEntity.ok(institutionMapper.toInstitutionDto(updatedInstitution));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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


    public List<InstitutionDto> filterAndSearchInstitutions(InstitutionType institutionType, String searchText) {
        List<Institution> institutions = institutionRepository.filterAndSearch(institutionType, searchText);
        return institutions.stream()
                .map(institutionMapper::toInstitutionDto)
                .collect(Collectors.toList());
    }


}
