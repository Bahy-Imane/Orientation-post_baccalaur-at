//package com.orientation.biorbac;
//
//import com.biorbac.dto.InstitutionDto;
//import com.biorbac.enums.InstitutionType;
//import com.biorbac.mapper.InstitutionMapper;
//import com.biorbac.model.Institution;
//import com.biorbac.model.Review;
//import com.biorbac.repository.InstitutionRepository;
//import com.biorbac.repository.ReviewRepository;
//import com.biorbac.service.InstitutionService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class InstitutionServiceTest {
//
//    @Mock
//    private InstitutionRepository institutionRepository;
//
//    @Mock
//    private ReviewRepository reviewRepository;
//
//    @Mock
//    private InstitutionMapper institutionMapper;
//
//    @InjectMocks
//    private InstitutionService institutionService;
//
//    private Institution institution;
//    private InstitutionDto institutionDto;
//    private Review review;
//
//    @BeforeEach
//    public void setup() {
//        institution = new Institution();
//        institution.setInstitutionId(1L);
//        institution.setInstitutionName("Test Institution");
//
//        institutionDto = new InstitutionDto();
//        institutionDto.setInstitutionName("Test Institution");
//
//        review = new Review();
//        review.setRating(5);
//    }
//
//    @Test
//    public void testGetAllInstitutions() {
//        when(institutionRepository.findAll()).thenReturn(Arrays.asList(institution));
//        when(institutionMapper.toInstitutionDto(institution)).thenReturn(institutionDto);
//        List<InstitutionDto> result = institutionService.getAllInstitutions();
//        assertEquals(1, result.size());
//        assertEquals("Test Institution", result.get(0).getInstitutionName());
//    }
//
//    @Test
//    public void testGetInstitutionById() {
//        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
//        when(institutionMapper.toInstitutionDto(institution)).thenReturn(institutionDto);
//        InstitutionDto result = institutionService.getInstitutionById(1L);
//        assertEquals("Test Institution", result.getInstitutionName());
//    }
//
//    @Test
//    public void testGetInstitutionByIdNotFound() {
//        when(institutionRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(RuntimeException.class, () -> institutionService.getInstitutionById(1L));
//    }
//
//    @Test
//    public void testCreateInstitution() {
//        when(institutionMapper.toInstitution(institutionDto)).thenReturn(institution);
//        when(institutionRepository.save(institution)).thenReturn(institution);
//        when(institutionMapper.toInstitutionDto(institution)).thenReturn(institutionDto);
//        InstitutionDto result = institutionService.createInstitution(institutionDto);
//        assertEquals("Test Institution", result.getInstitutionName());
//    }
//
//    @Test
//    public void testUpdateInstitution() {
//        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
//        when(institutionRepository.save(institution)).thenReturn(institution);
//        when(institutionMapper.toInstitutionDto(institution)).thenReturn(institutionDto);
//        ResponseEntity<InstitutionDto> response = institutionService.updateInstitution(1L, institutionDto);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Test Institution", response.getBody().getInstitutionName());
//    }
//
//    @Test
//    public void testUpdateInstitutionNotFound() {
//        when(institutionRepository.findById(1L)).thenReturn(Optional.empty());
//        ResponseEntity<InstitutionDto> response = institutionService.updateInstitution(1L, institutionDto);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    public void testDeleteInstitution() {
//        when(institutionRepository.existsById(1L)).thenReturn(true);
//        ResponseEntity<Void> response = institutionService.deleteInstitution(1L);
//        verify(institutionRepository, times(1)).deleteById(1L);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }
//
//    @Test
//    public void testDeleteInstitutionNotFound() {
//        when(institutionRepository.existsById(1L)).thenReturn(false);
//        ResponseEntity<Void> response = institutionService.deleteInstitution(1L);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    public void testCalculateAverageRating() {
//        when(reviewRepository.findByInstitution_InstitutionId(1L)).thenReturn(Arrays.asList(review));
//        double averageRating = institutionService.calculateAverageRating(1L);
//        assertEquals(5.0, averageRating);
//    }
//
//    @Test
//    public void testCalculateAverageRatingNoReviews() {
//        when(reviewRepository.findByInstitution_InstitutionId(1L)).thenReturn(Collections.emptyList());
//        double averageRating = institutionService.calculateAverageRating(1L);
//        assertEquals(0.0, averageRating);
//    }
//}
