package com.biorbac.controller;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.dto.ReviewDto;
import com.biorbac.enums.BacType;
import com.biorbac.service.FieldOfStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fields-of-study")
@CrossOrigin("http://localhost:4200")
public class FieldOfStudyController {

    @Autowired
    private FieldOfStudyService fieldOfStudyService;


    @GetMapping("/all-fields")
    public List<FieldOfStudyDto> getAllFieldsOfStudy() {
        return fieldOfStudyService.getAllFieldOfStudy();
    }

    @GetMapping("/institution/{institutionId}")
    public List<FieldOfStudyDto> getFieldOfStudiesByInstitutionId(@PathVariable Long institutionId) {
        return fieldOfStudyService.getFieldOfStudiesByInstitutionId(institutionId);
    }

    @GetMapping("/{id}")
    public List<FieldOfStudyDto> getFieldOfStudyById(@PathVariable Long id) {
    return fieldOfStudyService.getFieldOfStudyById(id);
   }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-field-of-study")
    public Map<String, String> addFieldOfStudy(@RequestBody FieldOfStudyDto fieldOfStudyDto) {
        return fieldOfStudyService.addFieldOfStudy(fieldOfStudyDto);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{fosId}")
    public Map<String, String> updateFieldOfStudy(@PathVariable Long fosId,
                                                              @RequestBody FieldOfStudyDto fieldOfStudyDto) {
       return fieldOfStudyService.updateFieldOfStudy(fosId,fieldOfStudyDto);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{fosId}")
    public ResponseEntity<Void> deleteFieldOfStudy(@PathVariable Long fosId) {
        fieldOfStudyService.deleteFieldOfStudy(fosId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/recommendation")
    public List<FieldOfStudyDto> getRecommendation(){
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
