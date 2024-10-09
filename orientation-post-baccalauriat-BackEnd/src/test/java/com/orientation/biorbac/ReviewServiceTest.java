//package com.orientation.biorbac;
//
//import com.biorbac.dto.ReviewDto;
//import com.biorbac.mapper.ReviewMapper;
//import com.biorbac.model.Institution;
//import com.biorbac.model.Review;
//import com.biorbac.model.Student;
//import com.biorbac.repository.InstitutionRepository;
//import com.biorbac.repository.ReviewRepository;
//import com.biorbac.repository.StudentRepository;
//import com.biorbac.service.ReviewService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class ReviewServiceTest {
//
//    @InjectMocks
//    private ReviewService reviewService;
//
//    @Mock
//    private ReviewRepository reviewRepository;
//
//    @Mock
//    private ReviewMapper reviewMapper;
//
//    @Mock
//    private StudentRepository studentRepository;
//
//    @Mock
//    private InstitutionRepository institutionRepository;
//
//    private Student student;
//    private Institution institution;
//    private Review review;
//    private ReviewDto reviewDto;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        student = new Student();
//        student.setUserId(1L);
//
//        institution = new Institution();
//        institution.setInstitutionId(1L);
//
//        review = new Review();
//        review.setStudent(student);
//        review.setInstitution(institution);
//
//        reviewDto = new ReviewDto();
//        reviewDto.setStudent(student);
//        reviewDto.setInstitution(institution);
//    }
//
//    @Test
//    void testGetReviewsByInstitution() {
//        when(reviewRepository.findByInstitution_InstitutionId(1L)).thenReturn(Arrays.asList(review));
//        when(reviewMapper.toReviewDto(review)).thenReturn(reviewDto);
//
//        List<ReviewDto> result = reviewService.getReviewsByInstitution(1L);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(reviewDto, result.get(0));
//
//        verify(reviewRepository, times(1)).findByInstitution_InstitutionId(1L);
//        verify(reviewMapper, times(1)).toReviewDto(review);
//    }
//
//    @Test
//    void testAddReview() {
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
//        when(reviewMapper.toReview(reviewDto)).thenReturn(review);
//        when(reviewRepository.save(any(Review.class))).thenReturn(review);
//        when(reviewMapper.toReviewDto(review)).thenReturn(reviewDto);
//
//        ReviewDto result = reviewService.addReview(reviewDto);
//
//        assertNotNull(result);
//        assertEquals(reviewDto, result);
//
//        verify(studentRepository, times(1)).findById(1L);
//        verify(institutionRepository, times(1)).findById(1L);
//        verify(reviewRepository, times(1)).save(review);
//    }
//
//    @Test
//    void testAddReviewStudentNotFound() {
//        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> reviewService.addReview(reviewDto));
//        assertEquals("Student not found", exception.getMessage());
//    }
//
//    @Test
//    void testAddReviewInstitutionNotFound() {
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(institutionRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> reviewService.addReview(reviewDto));
//        assertEquals("Institution not found", exception.getMessage());
//    }
//}
