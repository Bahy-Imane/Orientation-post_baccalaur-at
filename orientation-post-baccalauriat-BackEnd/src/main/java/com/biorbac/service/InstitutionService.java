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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Map<String , String> createInstitution(InstitutionDto institutionDto) {

        Institution institution = new Institution();
        institution.setInstitutionId(institutionDto.getInstitutionId());
        institution.setInstitutionName(institutionDto.getInstitutionName());
        institution.setInstitutionType(institutionDto.getInstitutionType());
        institution.setDescription(institutionDto.getDescription());
        institution.setWebsite(institutionDto.getWebsite());
        institution.setAddress(institutionDto.getAddress());
        institution.setInstitutionLogo(institutionDto.getInstitutionLogo());

        institutionRepository.save(institution);
        Map<String , String> map = new HashMap<>();
        map.put("msg", "Review created");
        return map;
    }



//    public InstitutionDto createInstitution(InstitutionDto institutionDto) {
//        Institution institution = institutionMapper.toInstitutionDto(institutionDto);
//        Institution savedInstitution = institutionRepository.save(institution);
//        return institutionMapper.toInstitution(savedInstitution);
//    }

    public Map<String , String> updateInstitution(Long id, InstitutionDto institutionDto) {
            Institution institutionOpt = institutionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("institution not found"));;

        institutionOpt.setInstitutionName(institutionDto.getInstitutionName());
        institutionOpt.setInstitutionType(institutionDto.getInstitutionType());
        institutionOpt.setInstitutionLogo(institutionDto.getInstitutionLogo());
        institutionOpt.setDescription(institutionDto.getDescription());
        institutionOpt.setAddress(institutionDto.getAddress());
        institutionOpt.setWebsite(institutionDto.getWebsite());

        institutionRepository.save(institutionOpt);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Field of study created");

        return  response;
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
