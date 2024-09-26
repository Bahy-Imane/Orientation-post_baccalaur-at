package com.biorbac.controller;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.service.FieldOfStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields-of-study")
public class FieldOfStudyController {

    @Autowired
    private FieldOfStudyService fieldOfStudyService;

    @GetMapping
    public ResponseEntity<List<FieldOfStudy>> getAllFieldsOfStudy() {
        List<FieldOfStudy> fieldsOfStudy = fieldOfStudyService.getAllFieldOfStudy();
        return ResponseEntity.ok(fieldsOfStudy);
    }

    // Add new field of study
    @PostMapping("/add/{departmentId}")
    public ResponseEntity<FieldOfStudyDto> addFieldOfStudy(@PathVariable Long departmentId,
                                                           @RequestBody FieldOfStudyDto fieldOfStudyDto) {
        FieldOfStudyDto newFieldOfStudy = fieldOfStudyService.AddFieldOfStudy(departmentId, fieldOfStudyDto);
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
}
