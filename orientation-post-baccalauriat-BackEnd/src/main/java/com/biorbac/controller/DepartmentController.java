package com.biorbac.controller;

import com.biorbac.dto.DepartmentDto;
import com.biorbac.model.Department;
import com.biorbac.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

//    @GetMapping("/department-institution")
//    public ResponseEntity<List<Department>> findDepartmentBYInstitutionsId(@RequestParam Long institutionId) {
//        List<Department> department = departmentService.findDepartmentBYInstitutionsId(institutionId);
//        return ResponseEntity.ok(department);
//    }

    @GetMapping
    public ResponseEntity<Department> getDepartmentById(@RequestParam Long id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping("/institution/{institutionId}")
    public ResponseEntity<DepartmentDto> addDepartment(@PathVariable Long institutionId,@RequestBody DepartmentDto departmentDto) {
        DepartmentDto newAcademicUnit = departmentService.addDepartment(institutionId,departmentDto);
        return ResponseEntity.ok(newAcademicUnit);
    }

    @PutMapping("/update")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestParam Long academicId,
                                                            @RequestBody DepartmentDto departmentDto) {
        DepartmentDto updatedAcademicUnit = departmentService.updateDepartment(academicId, departmentDto);
        return ResponseEntity.ok(updatedAcademicUnit);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDepartment(@RequestParam Long academicId) {
        departmentService.deleteDepartment(academicId);
        return ResponseEntity.noContent().build();
    }
}
