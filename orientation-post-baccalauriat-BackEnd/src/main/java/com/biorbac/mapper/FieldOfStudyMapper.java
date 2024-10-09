package com.biorbac.mapper;

import com.biorbac.dto.FieldOfStudyDto;
import com.biorbac.model.FieldOfStudy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FieldOfStudyMapper {

    FieldOfStudy toFieldOfStudy(FieldOfStudyDto fieldOfStudyDto);
    FieldOfStudyDto toFieldOfStudyDto(FieldOfStudy fieldOfStudy);
    void updateFieldOfStudyFromDto(FieldOfStudyDto fieldOfStudyDto, @MappingTarget FieldOfStudy fieldOfStudy);

}
