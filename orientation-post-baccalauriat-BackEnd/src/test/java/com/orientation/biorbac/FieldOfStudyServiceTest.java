package com.orientation.biorbac;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.Department;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.repository.DepartmentRepository;
import com.biorbac.repository.FieldOfStudyRepository;
import com.biorbac.service.FieldOfStudyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FieldOfStudyServiceTest {

    @Mock
    private FieldOfStudyRepository fieldOfStudyRepository;

    @Mock
    private FieldOfStudyMapper fieldOfStudyMapper;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private FieldOfStudyService fieldOfStudyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFieldOfStudy_Success() {
        Long departmentId = 1L;
        Department department = new Department();
        department.setDepartmentId(departmentId);

        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
        fieldOfStudyDto.setName("Computer Science");

        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setName("Computer Science");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(fieldOfStudyMapper.toFieldOfStudyDto(fieldOfStudyDto)).thenReturn(fieldOfStudy);
        when(fieldOfStudyRepository.save(any(FieldOfStudy.class))).thenReturn(fieldOfStudy);
        when(fieldOfStudyMapper.toFieldOfStudy(fieldOfStudy)).thenReturn(fieldOfStudyDto);

        FieldOfStudyDto result = fieldOfStudyService.AddFieldOfStudy(departmentId, fieldOfStudyDto);

        assertNotNull(result);
        assertEquals("Computer Science", result.getName());
        verify(departmentRepository, times(1)).findById(departmentId);
        verify(fieldOfStudyRepository, times(1)).save(any(FieldOfStudy.class));
    }

    @Test
    void testUpdateFieldOfStudy_Success() {
        Long fosId = 1L;
        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
        fieldOfStudyDto.setName("Mathematics");

        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setName("Physics");

        when(fieldOfStudyRepository.findById(fosId)).thenReturn(Optional.of(fieldOfStudy));
        doNothing().when(fieldOfStudyMapper).updateFieldOfStudyFromDto(fieldOfStudyDto, fieldOfStudy);
        when(fieldOfStudyRepository.save(any(FieldOfStudy.class))).thenReturn(fieldOfStudy);
        when(fieldOfStudyMapper.toFieldOfStudy(fieldOfStudy)).thenReturn(fieldOfStudyDto);

        FieldOfStudyDto result = fieldOfStudyService.updateFieldOfStudy(fosId, fieldOfStudyDto);

        assertNotNull(result);
        assertEquals("Mathematics", result.getName());
        verify(fieldOfStudyRepository, times(1)).findById(fosId);
        verify(fieldOfStudyRepository, times(1)).save(fieldOfStudy);
    }

    @Test
    void testDeleteFieldOfStudy_Success() {
        Long fosId = 1L;

        doNothing().when(fieldOfStudyRepository).deleteById(fosId);

        fieldOfStudyService.deleteFieldOfStudy(fosId);

        verify(fieldOfStudyRepository, times(1)).deleteById(fosId);
    }

    @Test
    void testAddFieldOfStudy_DepartmentNotFound() {
        Long departmentId = 999L;
        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                fieldOfStudyService.AddFieldOfStudy(departmentId, fieldOfStudyDto)
        );

        assertEquals("Department not found with ID: " + departmentId, exception.getMessage());
        verify(departmentRepository, times(1)).findById(departmentId);
        verify(fieldOfStudyRepository, never()).save(any(FieldOfStudy.class));
    }
}

