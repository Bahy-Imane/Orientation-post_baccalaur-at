package com.biorbac.controller;

import com.biorbac.dto.InstitutionDto;
import com.biorbac.enums.InstitutionType;
import com.biorbac.model.Institution;
import com.biorbac.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/institutions")
@CrossOrigin("http://localhost:4200")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;


    @GetMapping("/all-institutions")
    public List<InstitutionDto> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/{institutionId}")
    public InstitutionDto getInstitutionById(@PathVariable Long institutionId) {
        return institutionService.getInstitutionById(institutionId);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-institution")
    public InstitutionDto createInstitution(@RequestBody InstitutionDto institutionDto) {
        return institutionService.createInstitution(institutionDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<InstitutionDto> updateInstitution(@PathVariable Long id, @RequestBody InstitutionDto institutionDto) {
        return institutionService.updateInstitution(id, institutionDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        return institutionService.deleteInstitution(id);
    }

    @GetMapping("/filter-institutions")
    public ResponseEntity<List<InstitutionDto>> filterAndSearchInstitutions(
            @RequestParam(required = false) InstitutionType institutionType,
            @RequestParam(required = false, defaultValue = "") String searchText) {
        List<InstitutionDto> institutions = institutionService.filterAndSearchInstitutions(institutionType, searchText);
        return ResponseEntity.ok(institutions);
    }

}
