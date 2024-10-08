//package com.orientation.biorbac;
//
//import com.biorbac.dto.FieldOfStudyDto;
//import com.biorbac.mapper.FieldOfStudyMapper;
//import com.biorbac.model.FieldOfStudy;
//import com.biorbac.model.Institution;
//import com.biorbac.repository.FieldOfStudyRepository;
//import com.biorbac.repository.InstitutionRepository;
//import com.biorbac.service.FieldOfStudyService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class FieldOfStudyServiceTest {
//
//    private FieldOfStudyService fieldOfStudyService;
//
//    private FieldOfStudyRepository fieldOfStudyRepository;
//    private FieldOfStudyMapper fieldOfStudyMapper;
//    private InstitutionRepository institutionRepository;
//
//    @BeforeEach
//    void setUp() {
//        fieldOfStudyRepository = mock(FieldOfStudyRepository.class);
//        fieldOfStudyMapper = mock(FieldOfStudyMapper.class);
//        institutionRepository = mock(InstitutionRepository.class);
//
//        fieldOfStudyService = new FieldOfStudyService();
//        fieldOfStudyService.fieldOfStudyRepository = fieldOfStudyRepository;
//        fieldOfStudyService.fieldOfStudyMapper = fieldOfStudyMapper;
//        fieldOfStudyService.institutionRepository = institutionRepository;
//    }
//
//    @Test
//    void testGetAllFieldOfStudy() {
//        // Given
//        FieldOfStudy fieldOfStudy = new FieldOfStudy();
//        when(fieldOfStudyRepository.findAll()).thenReturn(List.of(fieldOfStudy));
//
//        // When
//        List<FieldOfStudy> result = fieldOfStudyService.getAllFieldOfStudy();
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(fieldOfStudy, result.get(0));
//    }
//
//    @Test
//    void testAddFieldOfStudy() {
//        // Given
//        Long departmentId = 1L;
//        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
//        Institution institution = new Institution();
//        FieldOfStudy fieldOfStudy = new FieldOfStudy();
//
//        when(institutionRepository.findById(departmentId)).thenReturn(Optional.of(institution));
//        when(fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudyDto)).thenReturn(fieldOfStudy);
//        when(fieldOfStudyRepository.save(fieldOfStudy)).thenReturn(fieldOfStudy);
//        when(fieldOfStudyMapper.toFieldOfStudy(fieldOfStudy)).thenReturn(fieldOfStudyDto);
//
//        // When
//        FieldOfStudyDto result = fieldOfStudyService.AddFieldOfStudy(departmentId, fieldOfStudyDto);
//
//        // Then
//        assertNotNull(result);
//        verify(institutionRepository).findById(departmentId);
//        verify(fieldOfStudyMapper).toFieldOfStudyDto(fieldOfStudyDto);
//        verify(fieldOfStudyMapper).toFieldOfStudy(fieldOfStudy);
//    }
//
//    @Test
//    void testUpdateFieldOfStudy() {
//        // Given
//        Long fosId = 1L;
//        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
//        FieldOfStudy fieldOfStudy = new FieldOfStudy();
//
//        when(fieldOfStudyRepository.findById(fosId)).thenReturn(Optional.of(fieldOfStudy));
//        when(fieldOfStudyMapper.toFieldOfStudy(fieldOfStudy)).thenReturn(fieldOfStudyDto);
//
//        // When
//        FieldOfStudyDto result = fieldOfStudyService.updateFieldOfStudy(fosId, fieldOfStudyDto);
//
//        // Then
//        assertNotNull(result);
//        verify(fieldOfStudyRepository).findById(fosId);
//        verify(fieldOfStudyMapper).updateFieldOfStudyFromDto(fieldOfStudyDto, fieldOfStudy);
//        verify(fieldOfStudyRepository).save(fieldOfStudy);
//    }
//
//    @Test
//    void testDeleteFieldOfStudy() {
//        // Given
//        Long fieldOfStudyId = 1L;
//
//        // When
//        fieldOfStudyService.deleteFieldOfStudy(fieldOfStudyId);
//
//        // Then
//        verify(fieldOfStudyRepository).deleteById(fieldOfStudyId);
//    }
//
//    @Test
//    void testAddFieldOfStudy_InstitutionNotFound() {
//        // Given
//        Long departmentId = 1L;
//        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
//
//        when(institutionRepository.findById(departmentId)).thenReturn(Optional.empty());
//
//        // When & Then
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            fieldOfStudyService.AddFieldOfStudy(departmentId, fieldOfStudyDto);
//        });
//        assertEquals("Institution not found with ID: " + departmentId, exception.getMessage());
//    }
//
//    @Test
//    void testUpdateFieldOfStudy_FieldOfStudyNotFound() {
//        // Given
//        Long fosId = 1L;
//        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
//        fieldOfStudyDto.setName("Test Name");
//
//        when(fieldOfStudyRepository.findById(fosId)).thenReturn(Optional.empty());
//
//        // When & Then
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            fieldOfStudyService.updateFieldOfStudy(fosId, fieldOfStudyDto);
//        });
//        assertEquals("Field Of Study not found", exception.getMessage());
//    }
//
//    @Test
//    void testUpdateFieldOfStudy_NameIsNull() {
//        // Given
//        Long fosId = 1L;
//        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
//
//        // When & Then
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            fieldOfStudyService.updateFieldOfStudy(fosId, fieldOfStudyDto);
//        });
//        assertEquals("Field Of Study unit name cannot be null or empty", exception.getMessage());
//    }
//}
