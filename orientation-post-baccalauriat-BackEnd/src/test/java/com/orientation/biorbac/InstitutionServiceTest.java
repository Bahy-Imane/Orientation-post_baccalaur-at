package com.orientation.biorbac;

import com.biorbac.dto.InstitutionDto;
import com.biorbac.enums.InstitutionType;
import com.biorbac.mapper.InstitutionMapper;
import com.biorbac.model.Institution;
import com.biorbac.model.Review;
import com.biorbac.repository.InstitutionRepository;
import com.biorbac.repository.ReviewRepository;
import com.biorbac.service.InstitutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InstitutionServiceTest {

    @InjectMocks
    private InstitutionService institutionService;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private InstitutionMapper institutionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllInstitutions() {
        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        institution.setInstitutionName("Test Institution");

        when(institutionRepository.findAll()).thenReturn(Collections.singletonList(institution));
        when(institutionMapper.toInstitutionDto(any())).thenReturn(new InstitutionDto());

        List<InstitutionDto> institutions = institutionService.getAllInstitutions();

        assertNotNull(institutions);
        assertEquals(1, institutions.size());
        verify(institutionRepository, times(1)).findAll();
    }

    @Test
    void getInstitutionById_Exists() {
        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        institution.setInstitutionName("Test Institution");

        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        when(institutionMapper.toInstitutionDto(any())).thenReturn(new InstitutionDto());

        InstitutionDto institutionDto = institutionService.getInstitutionById(1L);

        assertNotNull(institutionDto);
        verify(institutionRepository, times(1)).findById(1L);
    }

    @Test
    void getInstitutionById_NotFound() {
        when(institutionRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            institutionService.getInstitutionById(1L);
        });

        assertEquals("Institution not found", exception.getMessage());
    }

    @Test
    void createInstitution() {
        InstitutionDto institutionDto = new InstitutionDto();
        institutionDto.setInstitutionId(1L);
        institutionDto.setInstitutionName("Test Institution");

        when(institutionMapper.toInstitutionDto(any())).thenReturn(institutionDto);

        Map<String, String> response = institutionService.createInstitution(institutionDto);

        assertEquals("Review created", response.get("msg"));
        ArgumentCaptor<Institution> institutionCaptor = ArgumentCaptor.forClass(Institution.class);
        verify(institutionRepository, times(1)).save(institutionCaptor.capture());
        assertEquals("Test Institution", institutionCaptor.getValue().getInstitutionName());
    }

    @Test
    void updateInstitution() {
        Institution existingInstitution = new Institution();
        existingInstitution.setInstitutionId(1L);
        existingInstitution.setInstitutionName("Old Name");

        InstitutionDto institutionDto = new InstitutionDto();
        institutionDto.setInstitutionName("New Name");

        when(institutionRepository.findById(1L)).thenReturn(Optional.of(existingInstitution));

        Map<String, String> response = institutionService.updateInstitution(1L, institutionDto);

        assertEquals("Field of study created", response.get("msg"));
        assertEquals("New Name", existingInstitution.getInstitutionName());
        verify(institutionRepository, times(1)).save(existingInstitution);
    }

    @Test
    void deleteInstitution_Exists() {
        when(institutionRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = institutionService.deleteInstitution(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(institutionRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteInstitution_NotFound() {
        when(institutionRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = institutionService.deleteInstitution(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(institutionRepository, never()).deleteById(1L);
    }

    @Test
    void searchByText() {
        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        institution.setInstitutionName("Search Test");

        when(institutionRepository.searchByText("Search")).thenReturn(Collections.singletonList(institution));
        when(institutionMapper.toInstitutionDto(any())).thenReturn(new InstitutionDto());

        List<InstitutionDto> results = institutionService.searchByText("Search");

        assertEquals(1, results.size());
        verify(institutionRepository, times(1)).searchByText("Search");
    }

    @Test
    void filterByType() {
        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        institution.setInstitutionName("Filtered Institution");

        when(institutionRepository.findByInstitutionType(InstitutionType.PUBLIC_UNIVERSITY)).thenReturn(Collections.singletonList(institution));
        when(institutionMapper.toInstitutionDto(any())).thenReturn(new InstitutionDto());

        List<InstitutionDto> results = institutionService.filterByType(InstitutionType.PUBLIC_UNIVERSITY);

        assertEquals(1, results.size());
        verify(institutionRepository, times(1)).findByInstitutionType(InstitutionType.PUBLIC_UNIVERSITY);
    }

    @Test
    void filterByRating() {
        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        institution.setInstitutionName("Rated Institution");

        when(institutionRepository.findByAverageRatingGreaterThanEqual(4.0)).thenReturn(Collections.singletonList(institution));
        when(institutionMapper.toInstitutionDto(any())).thenReturn(new InstitutionDto());

        List<InstitutionDto> results = institutionService.filterByRating(4.0);

        assertEquals(1, results.size());
        verify(institutionRepository, times(1)).findByAverageRatingGreaterThanEqual(4.0);
    }
}
