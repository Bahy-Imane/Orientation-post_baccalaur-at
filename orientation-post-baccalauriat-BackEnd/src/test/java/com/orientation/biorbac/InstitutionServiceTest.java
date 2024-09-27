package com.orientation.biorbac;

import com.biorbac.dto.InstitutionDto;
import com.biorbac.mapper.InstitutionMapper;
import com.biorbac.model.Institution;
import com.biorbac.repository.InstitutionRepository;
import com.biorbac.service.InstitutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InstitutionServiceTest {

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private InstitutionMapper institutionMapper;

    @InjectMocks
    private InstitutionService institutionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInstitutions() {
        // Arrange
        List<Institution> institutions = new ArrayList<>();
        institutions.add(new Institution());
        when(institutionRepository.findAll()).thenReturn(institutions);

        // Act
        List<Institution> result = institutionService.getAllInstitutions();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(institutionRepository, times(1)).findAll();
    }

    @Test
    void testGetInstitutionById_Found() {
        // Arrange
        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        InstitutionDto institutionDto = new InstitutionDto();
        when(institutionMapper.toInstitution(institution)).thenReturn(institutionDto);

        // Act
        ResponseEntity<InstitutionDto> response = institutionService.getInstitutionById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(institutionRepository, times(1)).findById(1L);
        verify(institutionMapper, times(1)).toInstitution(institution);
    }

    @Test
    void testGetInstitutionById_NotFound() {
        // Arrange
        when(institutionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<InstitutionDto> response = institutionService.getInstitutionById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(institutionRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateInstitution() {
        // Arrange
        InstitutionDto institutionDto = new InstitutionDto();
        Institution institution = new Institution();
        when(institutionMapper.toInstitutionDto(institutionDto)).thenReturn(institution);
        when(institutionRepository.save(institution)).thenReturn(institution);
        InstitutionDto savedInstitutionDto = new InstitutionDto();
        when(institutionMapper.toInstitution(institution)).thenReturn(savedInstitutionDto);

        // Act
        InstitutionDto result = institutionService.createInstitution(institutionDto);

        // Assert
        assertNotNull(result);
        verify(institutionRepository, times(1)).save(institution);
        verify(institutionMapper, times(1)).toInstitutionDto(institutionDto);
        verify(institutionMapper, times(1)).toInstitution(institution);
    }

    @Test
    void testUpdateInstitution_Found() {
        // Arrange
        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        InstitutionDto institutionDto = new InstitutionDto();
        doNothing().when(institutionMapper).updateInstitutionFromDto(institutionDto, institution);
        when(institutionRepository.save(institution)).thenReturn(institution);
        InstitutionDto updatedInstitutionDto = new InstitutionDto();
        when(institutionMapper.toInstitution(institution)).thenReturn(updatedInstitutionDto);

        // Act
        ResponseEntity<InstitutionDto> response = institutionService.updateInstitution(1L, institutionDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(institutionRepository, times(1)).findById(1L);
        verify(institutionMapper, times(1)).updateInstitutionFromDto(institutionDto, institution);
        verify(institutionRepository, times(1)).save(institution);
    }

    @Test
    void testUpdateInstitution_NotFound() {
        // Arrange
        when(institutionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<InstitutionDto> response = institutionService.updateInstitution(1L, new InstitutionDto());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(institutionRepository, times(1)).findById(1L);
        verify(institutionRepository, times(0)).save(any());
    }

    @Test
    void testDeleteInstitution_Found() {
        // Arrange
        when(institutionRepository.existsById(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = institutionService.deleteInstitution(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(institutionRepository, times(1)).existsById(1L);
        verify(institutionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteInstitution_NotFound() {
        // Arrange
        when(institutionRepository.existsById(1L)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = institutionService.deleteInstitution(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(institutionRepository, times(1)).existsById(1L);
        verify(institutionRepository, times(0)).deleteById(anyLong());
    }
}
