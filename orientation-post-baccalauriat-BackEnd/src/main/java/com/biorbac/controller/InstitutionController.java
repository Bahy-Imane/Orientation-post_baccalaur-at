package com.biorbac.controller;

import com.biorbac.dto.InstitutionDto;
import com.biorbac.enums.InstitutionType;
import com.biorbac.model.Institution;
import com.biorbac.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public Map<String , String> createInstitution(@RequestBody InstitutionDto institutionDto) {
        return institutionService.createInstitution(institutionDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/edit/{id}")
    public Map<String , String> updateInstitution(@PathVariable Long id, @RequestBody InstitutionDto institutionDto) {
        return institutionService.updateInstitution(id, institutionDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        return institutionService.deleteInstitution(id);
    }


    @GetMapping("/search")
    public ResponseEntity<List<InstitutionDto>> searchInstitutions(
            @RequestParam(value = "searchText", required = false) String searchText) {
        List<InstitutionDto> institutions = institutionService.searchByText(searchText);
        return new ResponseEntity<>(institutions, HttpStatus.OK);
    }

    @GetMapping("/filter/type")
    public ResponseEntity<List<InstitutionDto>> filterInstitutionsByType(
            @RequestParam(value = "type", required = false) InstitutionType type) {
        List<InstitutionDto> institutions = institutionService.filterByType(type);
        return new ResponseEntity<>(institutions, HttpStatus.OK);
    }

    @GetMapping("/filter/rating")
    public ResponseEntity<List<InstitutionDto>> filterInstitutionsByRating(
            @RequestParam(value = "minRating", required = false) Double minRating) {
        List<InstitutionDto> institutions = institutionService.filterByRating(minRating);
        return new ResponseEntity<>(institutions, HttpStatus.OK);
    }

}
