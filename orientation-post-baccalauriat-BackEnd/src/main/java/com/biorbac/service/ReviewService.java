package com.biorbac.service;

import com.biorbac.dto.ReviewDto;
import com.biorbac.mapper.ReviewMapper;
import com.biorbac.model.Institution;
import com.biorbac.model.Review;
import com.biorbac.model.Student;
import com.biorbac.repository.InstitutionRepository;
import com.biorbac.repository.ReviewRepository;
import com.biorbac.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    public List<ReviewDto> getReviewsByInstitution(Long institutionId) {
        List<Review> reviews = reviewRepository.findByInstitution_InstitutionId(institutionId);
        return reviews.stream()
                .map(reviewMapper::toReviewDto)
                .toList();
    }

    public ReviewDto addReview(ReviewDto reviewDto) {
        Student student = studentRepository.findById(reviewDto.getStudent().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Institution institution = institutionRepository.findById(reviewDto.getInstitution().getInstitutionId())
                .orElseThrow(() -> new IllegalArgumentException("Institution not found"));

        Review review = reviewMapper.toReview(reviewDto);
        review.setStudent(student);
        review.setInstitution(institution);

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.toReviewDto(savedReview);
    }
}
