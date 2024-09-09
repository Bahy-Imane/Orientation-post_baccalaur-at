package com.biorbac.mapper;


import com.biorbac.model.FieldOfStudy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FieldOfStudyDto {

    FieldOfStudy toFieldOfStudyDto(FieldOfStudyDto fieldOfStudyDto);
    FieldOfStudyDto toFieldOfStudy(FieldOfStudy fieldOfStudy);
    void updateFieldOfStudyFromDto(FieldOfStudyDto fieldOfStudyDto, @MappingTarget FieldOfStudy fieldOfStudy);

}
