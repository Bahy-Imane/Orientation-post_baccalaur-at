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


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDto> getInstitutionById(@PathVariable Long id) {
        return institutionService.getInstitutionById(id);
    }

    @PostMapping("/add-institution")
    public InstitutionDto createInstitution(@RequestBody InstitutionDto institutionDto) {
        return institutionService.createInstitution(institutionDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstitutionDto> updateInstitution(@PathVariable Long id, @RequestBody InstitutionDto institutionDto) {
        return institutionService.updateInstitution(id, institutionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        return institutionService.deleteInstitution(id);
    }

    @GetMapping("/filter")
    public List<Institution> filterAndSortInstitutions(
            @RequestParam(required = false) InstitutionType institutionType,
            @RequestParam(required = false) String institutionName,
            @RequestParam(required = false) String address) {
        return institutionService.filterAndSortInstitutions(institutionType, institutionName, address);
    }
}
