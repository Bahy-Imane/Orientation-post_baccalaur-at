//package com.biorbac.controller;
//
//import com.biorbac.model.FieldOfStudy;
//import com.biorbac.model.Student;
//import com.biorbac.repository.StudentRepository;
//import com.biorbac.service.FieldOfStudyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/students")
//public class StudentController {
//
//    @Autowired
//    private FieldOfStudyService fieldOfStudyService;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @GetMapping("/{studentId}/recommendations")
//    public ResponseEntity<List<FieldOfStudy>> getRecommendations(@PathVariable Long studentId) {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//        List<FieldOfStudy> recommendations = fieldOfStudyService.recommend(student);
//        return ResponseEntity.ok(recommendations);
//    }
//}
