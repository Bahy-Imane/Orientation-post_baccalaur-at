package com.biorbac.controller;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.enums.BacType;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Student;
import com.biorbac.repository.StudentRepository;
import com.biorbac.service.FieldOfStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/fields-of-study")
@CrossOrigin("http://localhost:4200")
public class FieldOfStudyController {

    @Autowired
    private FieldOfStudyService fieldOfStudyService;


    @GetMapping("/all-fields")
    public ResponseEntity<List<FieldOfStudy>> getAllFieldsOfStudy() {
        List<FieldOfStudy> fieldsOfStudy = fieldOfStudyService.getAllFieldOfStudy();
        return ResponseEntity.ok(fieldsOfStudy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldOfStudyDto> getFieldOfStudyById(@PathVariable Long id) {
        return fieldOfStudyService.getFieldOfStudyById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-field-of-study")
    public ResponseEntity<FieldOfStudyDto> addFieldOfStudy(
            @RequestBody FieldOfStudyDto fieldOfStudyDto) {
        FieldOfStudyDto newFieldOfStudy = fieldOfStudyService.addFieldOfStudy(fieldOfStudyDto);
        return ResponseEntity.ok(newFieldOfStudy);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<FieldOfStudyDto> updateFieldOfStudy(@RequestParam Long fosId,
                                                              @RequestBody FieldOfStudyDto fieldOfStudyDto) {
        FieldOfStudyDto updatedFieldOfStudy = fieldOfStudyService.updateFieldOfStudy(fosId, fieldOfStudyDto);
        return ResponseEntity.ok(updatedFieldOfStudy);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFieldOfStudy(@RequestParam Long fieldOfStudyId) {
        fieldOfStudyService.deleteFieldOfStudy(fieldOfStudyId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/recommendation")
    public List<FieldOfStudy> getRecommendation(){
        return fieldOfStudyService.recommendBasedOnStudent();
    }

    @GetMapping("/filter-fields")
    public ResponseEntity<List<FieldOfStudyDto>> filterAndSearchFields(
            @RequestParam(required = false) BacType bacTypeRequired,
            @RequestParam(required = false) Double minimumBacNote,
            @RequestParam(required = false, defaultValue = "") String searchText) {
        List<FieldOfStudyDto> fieldsOfStudy = fieldOfStudyService.filterAndSearchFields(bacTypeRequired, minimumBacNote, searchText);
        return ResponseEntity.ok(fieldsOfStudy);
    }

}
