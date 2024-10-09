package com.orientation.biorbac;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.enums.BacType;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Institution;
import com.biorbac.model.Student;
import com.biorbac.repository.FieldOfStudyRepository;
import com.biorbac.repository.InstitutionRepository;
import com.biorbac.service.FieldOfStudyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FieldOfStudyServiceTest {

    @InjectMocks
    private FieldOfStudyService fieldOfStudyService;

    @Mock
    private FieldOfStudyRepository fieldOfStudyRepository;

    @Mock
    private FieldOfStudyMapper fieldOfStudyMapper;

    @Mock
    private InstitutionRepository institutionRepository;

    private FieldOfStudy fieldOfStudy;
    private FieldOfStudyDto fieldOfStudyDto;
    private Institution institution;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        institution = new Institution();
        institution.setInstitutionId(1L);

        fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setFosId(1L);
        fieldOfStudy.setName("Computer Science");
        fieldOfStudy.setInstitution(institution);

        fieldOfStudyDto = new FieldOfStudyDto();
        fieldOfStudyDto.setName("Computer Science");
        fieldOfStudyDto.setInstitutionId(1L);
    }

    @Test
    void testGetAllFieldOfStudy() {
        when(fieldOfStudyRepository.findAll()).thenReturn(Collections.singletonList(fieldOfStudy));
        when(fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudy)).thenReturn(fieldOfStudyDto);

        List<FieldOfStudy> result = fieldOfStudyService.getAllFieldOfStudy();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(fieldOfStudyRepository, times(1)).findAll();
    }

    @Test
    void testGetFieldOfStudyById_Found() {
        when(fieldOfStudyRepository.findById(1L)).thenReturn(Optional.of(fieldOfStudy));
        when(fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudy)).thenReturn(fieldOfStudyDto);

        ResponseEntity<FieldOfStudyDto> response = fieldOfStudyService.getFieldOfStudyById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Computer Science", response.getBody().getName());
    }

    @Test
    void testGetFieldOfStudyById_NotFound() {
        when(fieldOfStudyRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<FieldOfStudyDto> response = fieldOfStudyService.getFieldOfStudyById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddFieldOfStudy() {
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        when(fieldOfStudyMapper.toFieldOfStudy(fieldOfStudyDto)).thenReturn(fieldOfStudy);
        when(fieldOfStudyRepository.save(fieldOfStudy)).thenReturn(fieldOfStudy);
        when(fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudy)).thenReturn(fieldOfStudyDto);

        FieldOfStudyDto result = fieldOfStudyService.addFieldOfStudy(fieldOfStudyDto);

        assertNotNull(result);
        assertEquals("Computer Science", result.getName());
        verify(institutionRepository, times(1)).findById(1L);
        verify(fieldOfStudyRepository, times(1)).save(any(FieldOfStudy.class));
    }

    @Test
    void testUpdateFieldOfStudy() {
        when(fieldOfStudyRepository.findById(1L)).thenReturn(Optional.of(fieldOfStudy));
        when(fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudy)).thenReturn(fieldOfStudyDto);

        fieldOfStudyDto.setName("Updated Name");

        fieldOfStudyMapper.updateFieldOfStudyFromDto(fieldOfStudyDto, fieldOfStudy);

        when(fieldOfStudyRepository.save(fieldOfStudy)).thenReturn(fieldOfStudy);

        FieldOfStudyDto updatedResult = fieldOfStudyService.updateFieldOfStudy(1L, fieldOfStudyDto);

        assertNotNull(updatedResult);
        assertEquals("Updated Name", updatedResult.getName());
        verify(fieldOfStudyRepository, times(1)).findById(1L);
    }


    @Test
    void testDeleteFieldOfStudy() {
        fieldOfStudyService.deleteFieldOfStudy(1L);
        verify(fieldOfStudyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRecommendBasedOnStudent() {
        Student student = new Student();
        student.setBacScore(15.0);
        student.setBacType(BacType.SCIENTIFIC);
        when(fieldOfStudyRepository.findByMinimumBacNoteLessThanEqualAndBacTypeRequired(15.0, BacType.SCIENTIFIC))
                .thenReturn(Collections.singletonList(fieldOfStudy));

        List<FieldOfStudy> recommendations = fieldOfStudyService.recommendBasedOnStudent(student);

        assertNotNull(recommendations);
        assertEquals(1, recommendations.size());
        assertEquals("Computer Science", recommendations.get(0).getName());
    }
}
