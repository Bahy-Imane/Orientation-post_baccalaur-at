package com.biorbac.controller;

import com.biorbac.dto.InstitutionDto;
import com.biorbac.enums.Specialization;
import com.biorbac.model.Institution;
import com.biorbac.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @GetMapping("/all")
    public List<Institution> getInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @PostMapping("/add")
    public InstitutionDto addInstitution(@RequestBody InstitutionDto institutionDto) {
       return institutionService.addInstitution(institutionDto);
    }

    @PutMapping("/update")
    public InstitutionDto updateInstitution(@RequestParam Long institutionId, @RequestBody InstitutionDto institutionDto) {
        return institutionService.updateInstitution(institutionId, institutionDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Institution>> searchInstitutions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String emailContact,
            @RequestParam(required = false) Specialization specialization) {

        List<Institution> institutions = institutionService.findInstitution(
                name, subject, ville, description, emailContact, specialization);

        return ResponseEntity.ok(institutions);
    }

    @DeleteMapping("/{delete}")
    public ResponseEntity<String> deleteInstitution(@RequestParam Long institutionId) {
        institutionService.deleteInstitution(institutionId);
        return ResponseEntity.noContent().build();
    }
}
