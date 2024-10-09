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
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<ReviewDto> getReviewsByInstitutionId(Long institutionId) {
        List<Review> reviews = reviewRepository.findByInstitution_InstitutionId(institutionId);
        return reviews.stream().map(reviewMapper::toReviewDto).collect(Collectors.toList());
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        Institution institution = institutionRepository.findById(reviewDto.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        Student student = studentRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewMapper.toReview(reviewDto);
        review.setInstitution(institution);
        review.setStudent(student);

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toReviewDto(savedReview);
    }

    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDetails) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewMapper.updateReviewFromDto(reviewDetails, review);

        Institution institution = institutionRepository.findById(reviewDetails.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        Student student = studentRepository.findById(reviewDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        review.setInstitution(institution); // Update institution reference
        review.setStudent(student);          // Update student reference

        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toReviewDto(updatedReview);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review);
    }
}
