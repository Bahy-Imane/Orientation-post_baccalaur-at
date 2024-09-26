package com.biorbac.service;

import com.biorbac.dto.DepartmentDto;
import com.biorbac.mapper.DepartmentMapper;
import com.biorbac.model.Department;
import com.biorbac.model.Institution;
import com.biorbac.repository.DepartmentRepository;
import com.biorbac.repository.InstitutionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).get();
    }



    public DepartmentDto addDepartment(Long institutionId, DepartmentDto departmentDto) {
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found with ID: " + institutionId));

        Department department = departmentMapper.toDepartmentDto(departmentDto);
        department.setInstitution(institution);
        Department savedDepartment = departmentRepository.save(department);
        return departmentMapper.toDepartment(savedDepartment);
    }




    public DepartmentDto updateDepartment(Long academicId, DepartmentDto departmentDto) {
        if(departmentDto.getDepartmentName()==null || departmentDto.getDepartmentName().isEmpty()) {
            throw new IllegalArgumentException("Academic unit name cannot be null or empty");
        }

        Department department = departmentRepository.findById(academicId).orElseThrow(()->new RuntimeException("Academic unit not found"));

        departmentMapper.updateDepartmentFromDto(departmentDto, department);

        Department updatedAcademic = departmentRepository.save(department);

        return departmentMapper.toDepartment(updatedAcademic);
    }


    public void deleteDepartment(Long academicUnitId) {
        departmentRepository.deleteById(academicUnitId);
    }






}
