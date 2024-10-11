package com.biorbac.controller;

import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Student;
import com.biorbac.repository.StudentRepository;
import com.biorbac.service.FieldOfStudyService;
import com.biorbac.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all-students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

}
