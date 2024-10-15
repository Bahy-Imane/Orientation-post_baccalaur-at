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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testCreateReview() {
        ReviewDto reviewDto = new ReviewDto(); // Set properties for reviewDto
        Institution institution = new Institution(); // Set properties for institution
        Student student = new Student(); // Set properties for student

        when(institutionRepository.findById(reviewDto.getInstitutionId())).thenReturn(Optional.of(institution));
        when(studentRepository.findStudentByUserName(reviewDto.getUserName())).thenReturn(student);
        when(reviewRepository.save(any(Review.class))).thenReturn(new Review());

        HashMap<String, String> result = (HashMap<String, String>) reviewService.createReview(reviewDto);

        assertEquals("Review created", result.get("msg"));
        verify(institutionRepository).findById(reviewDto.getInstitutionId());
        verify(studentRepository).findStudentByUserName(reviewDto.getUserName());
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void testUpdateReview() {
        Long reviewId = 1L;
        ReviewDto reviewDto = new ReviewDto(); // Set properties for reviewDto
        Review existingReview = new Review(); // Set properties for existingReview

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(institutionRepository.findById(reviewDto.getInstitutionId())).thenReturn(Optional.of(new Institution()));
        when(studentRepository.findStudentByUserName(reviewDto.getUserName())).thenReturn(new Student());
        when(reviewRepository.save(existingReview)).thenReturn(existingReview);
        when(reviewMapper.toReviewDto(existingReview)).thenReturn(reviewDto);

        ReviewDto result = reviewService.updateReview(reviewId, reviewDto);

        assertNotNull(result);
        verify(reviewRepository).findById(reviewId);
        verify(reviewMapper).updateReviewFromDto(reviewDto, existingReview);
    }

    @Test
    void testDeleteReview() {
        Long reviewId = 1L;
        Review review = new Review(); // Set properties for review
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.deleteReview(reviewId);

        verify(reviewRepository).delete(review);
    }

//    @Test
//    void testGetAllReviews() {
//        Review review1 = new Review(); // Set properties for review1
//        Review review2 = new Review(); // Set properties for review2
//        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review1, review2));
//
//        List<ReviewDto> result = reviewService.getAllReviews();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(reviewRepository).findAll();
//    }
//
//    @Test
//    void testGetReviewsByInstitutionId() {
//        Long institutionId = 1L;
//        Review review = new Review(); // Set properties for the review
//        when(reviewRepository.findByInstitution_InstitutionId(institutionId)).thenReturn(Arrays.asList(review));
//
//        List<ReviewDto> result = reviewService.getReviewsByInstitutionId(institutionId);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(reviewRepository).findByInstitution_InstitutionId(institutionId);
//    }
//
//    @Test
//    void testGetReviewsByStudentName() {
//        String studentName = "JohnDoe";
//        Review review = new Review(); // Set properties for the review
//        when(reviewRepository.findByStudentUserName(studentName)).thenReturn(Arrays.asList(review));
//
//        List<ReviewDto> result = reviewService.getReviewsByStudentName(studentName);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(reviewRepository).findByStudentUserName(studentName);
//    }
}
