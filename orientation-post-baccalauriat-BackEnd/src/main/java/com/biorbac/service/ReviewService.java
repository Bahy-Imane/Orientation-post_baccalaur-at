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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    public ReviewRepository reviewRepository;

    @Autowired
    public ReviewMapper reviewMapper;

    @Autowired
    public InstitutionRepository institutionRepository;

    @Autowired
    public StudentRepository studentRepository;


    public List<ReviewDto> getAllReviews(){
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(rev->{
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewId(rev.getReviewId());
            reviewDto.setComment(rev.getComment());
            reviewDto.setInstitutionId(rev.getInstitution().getInstitutionId());
            reviewDto.setReviewId(rev.getReviewId());
            reviewDto.setUserName(rev.getStudent().getUsername());
            reviewDto.setRating(rev.getRating());
            return reviewDto;
        }).collect(Collectors.toList());
    }

    public List<ReviewDto> getReviewsByInstitutionId(Long institutionId) {
        List<Review> reviews = reviewRepository.findByInstitution_InstitutionId(institutionId);
        return reviews.stream().map(rev->{
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewId(rev.getReviewId());
            reviewDto.setComment(rev.getComment());
            reviewDto.setInstitutionId(rev.getInstitution().getInstitutionId());
            reviewDto.setReviewId(rev.getReviewId());
            reviewDto.setUserName(rev.getStudent().getUsername());
            reviewDto.setRating(rev.getRating());
            return reviewDto;
        }).collect(Collectors.toList());    }

    public Map<String , String> createReview(ReviewDto reviewDto) {
        Institution institution = institutionRepository.findById(reviewDto.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        Student student = studentRepository.findStudentByUserName(reviewDto.getUserName());

        Review review = new Review();
        review.setInstitution(institution);
        review.setStudent(student);
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        reviewRepository.save(review);
        Map<String , String> map = new HashMap<>();
        map.put("msg", "Review created");
        return map;
    }

    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDetails) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewMapper.updateReviewFromDto(reviewDetails, review);

        Institution institution = institutionRepository.findById(reviewDetails.getInstitutionId())
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        Student student = studentRepository.findStudentByUserName(reviewDetails.getUserName());

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
