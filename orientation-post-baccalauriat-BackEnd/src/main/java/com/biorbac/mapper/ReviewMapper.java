package com.biorbac.mapper;

import com.biorbac.dto.ReviewDto;
import com.biorbac.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    Review toReview(ReviewDto reviewDto);

    ReviewDto toReviewDto(Review review);

    void updateReviewFromDto(ReviewDto reviewDto, @MappingTarget Review review);
}

