package com.biorbac.mapper;

import com.biorbac.dto.InstitutionDto;
import com.biorbac.model.Institution;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InstitutionMapper {

    Institution toInstitutionDto(InstitutionDto institutionDto);
    InstitutionDto toInstitution(Institution institution);
    void updateInstitutionFromDto(InstitutionDto institutionDto, @MappingTarget Institution institution);

}
