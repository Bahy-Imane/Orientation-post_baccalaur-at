//package com.orientation.biorbac;
//
//import com.biorbac.dto.DepartmentDto;
//import com.biorbac.mapper.DepartmentMapper;
//import com.biorbac.model.Department;
//import com.biorbac.model.Institution;
//import com.biorbac.repository.DepartmentRepository;
//import com.biorbac.repository.InstitutionRepository;
//import com.biorbac.service.DepartmentService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class DepartmentServiceTest {
//
//    @Mock
//    private DepartmentRepository departmentRepository;
//
//    @Mock
//    private InstitutionRepository institutionRepository;
//
//    @Mock
//    private DepartmentMapper departmentMapper;
//
//    @InjectMocks
//    private DepartmentService departmentService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllDepartments() {
//        departmentService.getAllDepartments();
//        verify(departmentRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetDepartmentById_Success() {
//        Long departmentId = 1L;
//        Department department = new Department();
//        department.setDepartmentId(departmentId);
//
//        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
//
//        Department result = departmentService.getDepartmentById(departmentId);
//
//        assertNotNull(result);
//        assertEquals(departmentId, result.getDepartmentId());
//        verify(departmentRepository, times(1)).findById(departmentId);
//    }
//
//    @Test
//    void testAddDepartment_Success() {
//        Long institutionId = 1L;
//        Institution institution = new Institution();
//        institution.setInstitutionId(institutionId);
//
//        DepartmentDto departmentDto = new DepartmentDto();
//        departmentDto.setDepartmentName("Computer Science");
//
//        Department department = new Department();
//        department.setDepartmentName("Computer Science");
//
//        when(institutionRepository.findById(institutionId)).thenReturn(Optional.of(institution));
//        when(departmentMapper.toDepartmentDto(departmentDto)).thenReturn(department);
//        when(departmentRepository.save(any(Department.class))).thenReturn(department);
//        when(departmentMapper.toDepartment(department)).thenReturn(departmentDto);
//
//        DepartmentDto result = departmentService.addDepartment(institutionId, departmentDto);
//
//        assertNotNull(result);
//        assertEquals("Computer Science", result.getDepartmentName());
//        verify(institutionRepository, times(1)).findById(institutionId);
//        verify(departmentRepository, times(1)).save(any(Department.class));
//    }
//
//    @Test
//    void testAddDepartment_InstitutionNotFound() {
//        Long institutionId = 999L;
//        DepartmentDto departmentDto = new DepartmentDto();
//
//        when(institutionRepository.findById(institutionId)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(RuntimeException.class, () ->
//                departmentService.addDepartment(institutionId, departmentDto)
//        );
//
//        assertEquals("Institution not found with ID: " + institutionId, exception.getMessage());
//        verify(institutionRepository, times(1)).findById(institutionId);
//        verify(departmentRepository, never()).save(any(Department.class));
//    }
//
//    @Test
//    void testUpdateDepartment_Success() {
//        Long departmentId = 1L;
//        DepartmentDto departmentDto = new DepartmentDto();
//        departmentDto.setDepartmentName("Mathematics");
//
//        Department department = new Department();
//        department.setDepartmentName("Physics");
//
//        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
//        doNothing().when(departmentMapper).updateDepartmentFromDto(departmentDto, department);
//        when(departmentRepository.save(any(Department.class))).thenReturn(department);
//        when(departmentMapper.toDepartment(department)).thenReturn(departmentDto);
//
//        DepartmentDto result = departmentService.updateDepartment(departmentId, departmentDto);
//
//        assertNotNull(result);
//        assertEquals("Mathematics", result.getDepartmentName());
//        verify(departmentRepository, times(1)).findById(departmentId);
//        verify(departmentRepository, times(1)).save(department);
//    }
//
//    @Test
//    void testUpdateDepartment_DepartmentNotFound() {
//        Long departmentId = 999L;
//        DepartmentDto departmentDto = new DepartmentDto();
//        departmentDto.setDepartmentName("Biology");
//
//        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(RuntimeException.class, () ->
//                departmentService.updateDepartment(departmentId, departmentDto)
//        );
//
//        assertEquals("Academic unit not found", exception.getMessage());
//        verify(departmentRepository, times(1)).findById(departmentId);
//        verify(departmentRepository, never()).save(any(Department.class));
//    }
//
//    @Test
//    void testDeleteDepartment_Success() {
//        Long departmentId = 1L;
//
//        doNothing().when(departmentRepository).deleteById(departmentId);
//
//        departmentService.deleteDepartment(departmentId);
//
//        verify(departmentRepository, times(1)).deleteById(departmentId);
//    }
//}
//
