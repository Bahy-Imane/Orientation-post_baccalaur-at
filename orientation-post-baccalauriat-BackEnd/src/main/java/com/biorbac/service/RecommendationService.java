//package com.biorbac.service;
//
//import com.biorbac.enums.Interest;
//import com.biorbac.model.Institution;
//import com.biorbac.model.Student;
//import com.biorbac.repository.InstitutionRepository;
//import com.biorbac.repository.StudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class RecommendationService {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private InstitutionRepository institutionRepository;
//
//    public List<Institution> recommendInstitutions(Long studentId) {
//        Student student = studentRepository.findById(studentId).orElseThrow();
//
//
//        Interest studentSpecialization = studentA.getSpecialization();
//        String studentLocation = student.getLocation();
//
//        return institutionRepository.findAll().stream()
//                .filter(institution ->
//                        institution.getLocation().equalsIgnoreCase(studentLocation) ||
//                                institution.getSpecialization() == studentSpecialization)
//                .collect(Collectors.toList());
//    }
//}
