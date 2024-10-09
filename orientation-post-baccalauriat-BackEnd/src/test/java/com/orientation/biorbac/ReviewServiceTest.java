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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReviewsByInstitutionId() {
        Long institutionId = 1L;
        Review review = new Review();
        review.setRating(5); // Just an example
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setRating(5); // Just an example

        when(reviewRepository.findByInstitution_InstitutionId(institutionId)).thenReturn(Arrays.asList(review));
        when(reviewMapper.toReviewDto(review)).thenReturn(reviewDto);

        List<ReviewDto> result = reviewService.getReviewsByInstitutionId(institutionId);

        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getRating());
        verify(reviewRepository).findByInstitution_InstitutionId(institutionId);
    }

    @Test
    public void testCreateReview_Success() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setInstitutionId(1L);
        reviewDto.setUserId(2L);

        Institution institution = new Institution();
        Student student = new Student();
        Review review = new Review();

        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        when(studentRepository.findById(2L)).thenReturn(Optional.of(student));
        when(reviewMapper.toReview(reviewDto)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewMapper.toReviewDto(review)).thenReturn(reviewDto);

        ReviewDto result = reviewService.createReview(reviewDto);

        assertNotNull(result);
        verify(reviewRepository).save(review);
    }

    @Test
    public void testCreateReview_InstitutionNotFound() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setInstitutionId(1L);
        reviewDto.setUserId(2L);

        when(institutionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reviewService.createReview(reviewDto));
    }

    @Test
    public void testUpdateReview_Success() {
        Long reviewId = 1L;
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setInstitutionId(1L);
        reviewDto.setUserId(2L);

        Institution institution = new Institution();
        Student student = new Student();
        Review existingReview = new Review();
        existingReview.setReviewId(reviewId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        when(studentRepository.findById(2L)).thenReturn(Optional.of(student));
        when(reviewMapper.toReview(reviewDto)).thenReturn(existingReview);
        when(reviewRepository.save(existingReview)).thenReturn(existingReview);
        when(reviewMapper.toReviewDto(existingReview)).thenReturn(reviewDto);

        ReviewDto result = reviewService.updateReview(reviewId, reviewDto);

        assertNotNull(result);
        verify(reviewRepository).save(existingReview);
    }

    @Test
    public void testUpdateReview_ReviewNotFound() {
        Long reviewId = 1L;
        ReviewDto reviewDto = new ReviewDto();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reviewService.updateReview(reviewId, reviewDto));
    }

    @Test
    public void testDeleteReview_Success() {
        Long reviewId = 1L;
        Review review = new Review();
        review.setReviewId(reviewId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.deleteReview(reviewId);

        verify(reviewRepository).delete(review);
    }

    @Test
    public void testDeleteReview_NotFound() {
        Long reviewId = 1L;

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reviewService.deleteReview(reviewId));
    }
}
