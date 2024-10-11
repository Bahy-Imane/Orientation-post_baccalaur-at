package com.orientation.biorbac;

import com.biorbac.dto.ReviewDto;
import com.biorbac.mapper.ReviewMapper;
import com.biorbac.model.Institution;
import com.biorbac.model.Review;
import com.biorbac.model.Student;
import com.biorbac.repository.InstitutionRepository;
import com.biorbac.repository.ReviewRepository;
import com.biorbac.repository.StudentRepository;
import com.biorbac.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewServiceTest {


    private ReviewService reviewService;

    private ReviewRepository reviewRepository;
    private ReviewMapper reviewMapper;
    private InstitutionRepository institutionRepository;
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        reviewMapper = mock(ReviewMapper.class);
        institutionRepository = mock(InstitutionRepository.class);
        studentRepository = mock(StudentRepository.class);
        reviewService = new ReviewService();


        reviewService.reviewRepository = reviewRepository;
        reviewService.reviewMapper = reviewMapper;
        reviewService.institutionRepository = institutionRepository;
        reviewService.studentRepository = studentRepository;
    }

    @Test
    void testGetAllReviews() {
        // Arrange
        Review review = new Review();
        review.setReviewId(1L);
        review.setComment("Great institution!");
        review.setRating(5);

        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        review.setInstitution(institution);

        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Act
        List<ReviewDto> result = reviewService.getAllReviews();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Great institution!", result.get(0).getComment());
        assertEquals(1L, result.get(0).getInstitutionId());
    }

    @Test
    void testGetReviewsByInstitutionId() {
        // Arrange
        Review review = new Review();
        review.setReviewId(1L);
        review.setComment("Good place!");
        review.setRating(4);

        Institution institution = new Institution();
        institution.setInstitutionId(2L);
        review.setInstitution(institution);

        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByInstitution_InstitutionId(2L)).thenReturn(reviews);
        when(reviewMapper.toReviewDto(any())).thenReturn(new ReviewDto());

        // Act
        List<ReviewDto> result = reviewService.getReviewsByInstitutionId(2L);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testCreateReview() {
        // Arrange
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setInstitutionId(1L);
        reviewDto.setUserId(1L);
        reviewDto.setComment("Amazing experience!");
        reviewDto.setRating(5);

        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        Student student = new Student();
        student.setUserId(1L);

        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Act
        Map<String, String> response = reviewService.createReview(reviewDto);

        // Assert
        assertEquals("Review created", response.get("msg"));
        verify(reviewRepository).save(any(Review.class));
    }


    @Test
    void testUpdateReview() {
        // Arrange
        Review review = new Review();
        review.setReviewId(1L);
        review.setComment("Old comment");
        review.setRating(3);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setInstitutionId(1L);
        reviewDto.setUserId(1L);
        reviewDto.setComment("Updated comment");
        reviewDto.setRating(5);

        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        Student student = new Student();
        student.setUserId(1L);

        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Mock the mapping behavior
        when(reviewMapper.toReviewDto(any())).thenReturn(reviewDto);

        // Act
        ReviewDto updatedReviewDto = reviewService.updateReview(1L, reviewDto);

        // Assert
        assertEquals("Updated comment", updatedReviewDto.getComment());
        assertEquals(5, updatedReviewDto.getRating());

        // Verify that the institution and student were updated correctly
        assertEquals(institution, review.getInstitution());
        assertEquals(student, review.getStudent());

        // Verify that the repository save method was called
        verify(reviewRepository).save(review);
    }


    @Test
    void testDeleteReview() {
        // Arrange
        Review review = new Review();
        review.setReviewId(1L);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        // Act
        reviewService.deleteReview(1L);

        // Assert
        verify(reviewRepository).delete(review);
    }

    @Test
    void testCreateReview_InstitutionNotFound() {
        // Arrange
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setInstitutionId(1L);
        reviewDto.setUserId(1L);

        when(institutionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reviewService.createReview(reviewDto));
        assertEquals("Institution not found", exception.getMessage());
    }

    @Test
    void testCreateReview_UserNotFound() {
        // Arrange
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setInstitutionId(1L);
        reviewDto.setUserId(1L);

        Institution institution = new Institution();
        institution.setInstitutionId(1L);
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reviewService.createReview(reviewDto));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testUpdateReview_ReviewNotFound() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reviewService.updateReview(1L, new ReviewDto()));
        assertEquals("Review not found", exception.getMessage());
    }

    @Test
    void testDeleteReview_ReviewNotFound() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reviewService.deleteReview(1L));
        assertEquals("Review not found", exception.getMessage());
    }
}
