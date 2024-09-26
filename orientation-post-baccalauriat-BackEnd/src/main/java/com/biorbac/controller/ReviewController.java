package com.biorbac.controller;

import com.biorbac.dto.ReviewDto;
import com.biorbac.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/institution/{institutionId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByInstitution(@PathVariable Long institutionId) {
        List<ReviewDto> reviews = reviewService.getReviewsByInstitution(institutionId);
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewDto reviewDto) {
        ReviewDto createdReview = reviewService.addReview(reviewDto);
        return ResponseEntity.status(201).body(createdReview);
    }

}
