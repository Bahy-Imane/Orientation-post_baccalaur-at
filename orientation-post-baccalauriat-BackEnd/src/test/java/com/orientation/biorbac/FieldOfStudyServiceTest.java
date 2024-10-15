package com.orientation.biorbac;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.enums.BacType;
import com.biorbac.mapper.FieldOfStudyMapper;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Institution;
import com.biorbac.model.Student;
import com.biorbac.repository.FieldOfStudyRepository;
import com.biorbac.repository.InstitutionRepository;
import com.biorbac.repository.StudentRepository;
import com.biorbac.service.FieldOfStudyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FieldOfStudyServiceTest {

    @InjectMocks
    private FieldOfStudyService fieldOfStudyService;

    @Mock
    private FieldOfStudyRepository fieldOfStudyRepository;

    @Mock
    private FieldOfStudyMapper fieldOfStudyMapper;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFieldOfStudy() {
        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setFosId(1L);
        fieldOfStudy.setName("Computer Science");
        // Ajoutez d'autres propriétés nécessaires...

        when(fieldOfStudyRepository.findAll()).thenReturn(Collections.singletonList(fieldOfStudy));

        List<FieldOfStudyDto> result = fieldOfStudyService.getAllFieldOfStudy();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Computer Science", result.get(0).getName());
        verify(fieldOfStudyRepository, times(1)).findAll();
    }

//    @Test
//    public void testGetFieldOfStudyById() {
//        FieldOfStudy fieldOfStudy = new FieldOfStudy();
//        fieldOfStudy.setFosId(1L);
//        fieldOfStudy.setName("Mathematics");
//
//        when(fieldOfStudyRepository.findById(1L)).thenReturn(Optional.of(fieldOfStudy));
//
//        List<FieldOfStudyDto> result = fieldOfStudyService.getFieldOfStudyById(1L);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Mathematics", result.get(0).getName());
//        verify(fieldOfStudyRepository, times(1)).findById(1L);
//    }

    @Test
    public void testGetFieldOfStudyById_NotFound() {
        when(fieldOfStudyRepository.findById(1L)).thenReturn(Optional.empty());

        List<FieldOfStudyDto> result = fieldOfStudyService.getFieldOfStudyById(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(fieldOfStudyRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddFieldOfStudy() {
        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
        fieldOfStudyDto.setName("Physics");
        fieldOfStudyDto.setInstitutionId(1L);
        fieldOfStudyDto.setBacTypeRequired(BacType.SCIENTIFIC.name());

        Institution institution = new Institution();
        institution.setInstitutionId(1L);

        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));

        Map<String, String> response = fieldOfStudyService.addFieldOfStudy(fieldOfStudyDto);

        assertEquals("Field of study created", response.get("msg"));
        verify(fieldOfStudyRepository, times(1)).save(any(FieldOfStudy.class));
    }

    @Test
    public void testAddFieldOfStudy_InstitutionNotFound() {
        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
        fieldOfStudyDto.setInstitutionId(999L); // Un ID qui n'existe pas

        when(institutionRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fieldOfStudyService.addFieldOfStudy(fieldOfStudyDto));
        assertEquals("Institution not found", exception.getMessage());
    }



    @Test
    public void testUpdateFieldOfStudy_NotFound() {
        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
        when(fieldOfStudyRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fieldOfStudyService.updateFieldOfStudy(1L, fieldOfStudyDto));
        assertEquals("Field Of Study not found", exception.getMessage());
    }

    @Test
    public void testDeleteFieldOfStudy() {
        doNothing().when(fieldOfStudyRepository).deleteById(1L);
        fieldOfStudyService.deleteFieldOfStudy(1L);
        verify(fieldOfStudyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testSearchByText() {
        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setFosId(1L);
        fieldOfStudy.setName("Biology");

        when(fieldOfStudyRepository.searchByText("Bio")).thenReturn(Collections.singletonList(fieldOfStudy));
        when(fieldOfStudyMapper.toFieldOfStudyDto(any(FieldOfStudy.class))).thenReturn(new FieldOfStudyDto());

        List<FieldOfStudyDto> result = fieldOfStudyService.searchByText("Bio");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(fieldOfStudyRepository, times(1)).searchByText("Bio");
    }

    @Test
    public void testFilterByBacType() {
        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setFosId(1L);
        fieldOfStudy.setName("Chemistry");

        when(fieldOfStudyRepository.findByBacTypeRequired(BacType.SCIENTIFIC)).thenReturn(Collections.singletonList(fieldOfStudy));
        when(fieldOfStudyMapper.toFieldOfStudyDto(any(FieldOfStudy.class))).thenReturn(new FieldOfStudyDto());

        List<FieldOfStudyDto> result = fieldOfStudyService.filterByBacType(BacType.SCIENTIFIC.name());

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(fieldOfStudyRepository, times(1)).findByBacTypeRequired(BacType.SCIENTIFIC);
    }


//    @Test
//    public void testRecommendBasedOnStudent() {
//
//        Authentication loggedInUser = mock(Authentication.class);
//        SecurityContextHolder.getContext().setAuthentication(loggedInUser);
//        when(loggedInUser.getName()).thenReturn("studentUser");
//
//        Student student = new Student();
//        student.setBacScore(15);
//        student.setBacType(BacType.SCIENTIFIC);
//
//        when(studentRepository.findStudentByUserName("studentUser")).thenReturn(student);
//
//        FieldOfStudy fieldOfStudy = new FieldOfStudy();
//        fieldOfStudy.setFosId(1L);
//        fieldOfStudy.setName("Biology");
//        // Ajoutez d'autres propriétés nécessaires...
//
//        when(fieldOfStudyRepository.findByMinimumBacNoteLessThanEqualAndBacTypeRequired(15, BacType.SCIENTIFIC))
//                .thenReturn(Collections.singletonList(fieldOfStudy));
//
//        List<FieldOfStudyDto> result = fieldOfStudyService.recommendBasedOnStudent();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Biology", result.get(0).getName());
//    }


    //    @Test
//    public void testUpdateFieldOfStudy() {
//        FieldOfStudyDto fieldOfStudyDto = new FieldOfStudyDto();
//        fieldOfStudyDto.setName("Chemistry");
//
//        FieldOfStudy existingFieldOfStudy = new FieldOfStudy();
//        existingFieldOfStudy.setFosId(1L);
//        existingFieldOfStudy.setName("Biology");
//
//        when(fieldOfStudyRepository.findById(1L)).thenReturn(Optional.of(existingFieldOfStudy));
//
//        Map<String, String> response = fieldOfStudyService.updateFieldOfStudy(1L, fieldOfStudyDto);
//
//        assertEquals("Field of study created", response.get("msg"));
//        assertEquals("Chemistry", existingFieldOfStudy.getName());
//        verify(fieldOfStudyRepository, times(1)).save(existingFieldOfStudy);
//    }

}
