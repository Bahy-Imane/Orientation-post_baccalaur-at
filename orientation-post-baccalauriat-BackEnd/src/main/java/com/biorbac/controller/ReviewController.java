package com.biorbac.controller;

import com.biorbac.dto.ReviewDto;
import com.biorbac.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("http://localhost:4200")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/all-reviews")
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews();
    }


    @GetMapping("/institution/{institutionId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByInstitutionId(@PathVariable Long institutionId) {
        List<ReviewDto> reviews = reviewService.getReviewsByInstitutionId(institutionId);
        return ResponseEntity.ok(reviews);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping
    public Map<String, String> createReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.createReview(reviewDto);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/edit/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(reviewId, reviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
