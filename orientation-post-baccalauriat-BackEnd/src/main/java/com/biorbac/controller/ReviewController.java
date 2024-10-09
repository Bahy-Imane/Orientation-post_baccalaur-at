package com.biorbac.controller;

import com.biorbac.dto.ReviewDto;
import com.biorbac.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/institution/{institutionId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByInstitutionId(@PathVariable Long institutionId) {
        List<ReviewDto> reviews = reviewService.getReviewsByInstitutionId(institutionId);
        return ResponseEntity.ok(reviews);
    }
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        ReviewDto createdReview = reviewService.createReview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
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
