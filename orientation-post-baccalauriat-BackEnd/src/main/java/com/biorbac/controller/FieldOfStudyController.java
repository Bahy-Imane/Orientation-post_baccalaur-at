package com.biorbac.controller;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.dto.InstitutionDto;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Institution;
import com.biorbac.model.Student;
import com.biorbac.repository.StudentRepository;
import com.biorbac.service.FieldOfStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fields-of-study")
@CrossOrigin("http://localhost:4200")
public class FieldOfStudyController {

    @Autowired
    private FieldOfStudyService fieldOfStudyService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FieldOfStudyMapper fieldOfStudyMapper;

    @GetMapping
    public ResponseEntity<List<FieldOfStudy>> getAllFieldsOfStudy() {
        List<FieldOfStudy> fieldsOfStudy = fieldOfStudyService.getAllFieldOfStudy();
        return ResponseEntity.ok(fieldsOfStudy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldOfStudyDto> getFieldOfStudyById(@PathVariable Long id) {
        return fieldOfStudyService.getFieldOfStudyById(id);
    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/add")
//    public ResponseEntity<FieldOfStudyDto> addFieldOfStudy(@AuthenticationPrincipal Institution institution, @RequestBody FieldOfStudyDto fieldOfStudyDto) {
//        FieldOfStudyDto fieldOfStudyDto1 = fieldOfStudyService.addFieldOfStudy( institution, fieldOfStudyDto);
//        return new ResponseEntity<>(fieldOfStudyDto1, HttpStatus.CREATED);
//    }

    // Add new field of study
    @PostMapping("/add/{institutionId}")
    public ResponseEntity<FieldOfStudyDto> addFieldOfStudy(
            @PathVariable Long institutionId,
            @RequestBody FieldOfStudyDto fieldOfStudyDto) {
        FieldOfStudyDto newFieldOfStudy = fieldOfStudyService.addFieldOfStudy(institutionId, fieldOfStudyDto);
        return ResponseEntity.ok(newFieldOfStudy);
    }


    // Update field of study
    @PutMapping("/update")
    public ResponseEntity<FieldOfStudyDto> updateFieldOfStudy(@RequestParam Long fosId,
                                                              @RequestBody FieldOfStudyDto fieldOfStudyDto) {
        FieldOfStudyDto updatedFieldOfStudy = fieldOfStudyService.updateFieldOfStudy(fosId, fieldOfStudyDto);
        return ResponseEntity.ok(updatedFieldOfStudy);
    }

    // Delete field of study
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFieldOfStudy(@RequestParam Long fieldOfStudyId) {
        fieldOfStudyService.deleteFieldOfStudy(fieldOfStudyId);
        return ResponseEntity.noContent().build();
    }
//
//    @GetMapping("/{studentId}/recommendations")
//    public ResponseEntity<List<FieldOfStudyDto>> getRecommendations(@PathVariable Long studentId) {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'ID: " + studentId));
//
//        List<FieldOfStudy> recommendations = fieldOfStudyService.recommend(student);
//
//        List<FieldOfStudyDto> recommendationDtos = recommendations.stream()
//                .map(fieldOfStudyMapper::toFieldOfStudy)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(recommendationDtos);
//    }

}
