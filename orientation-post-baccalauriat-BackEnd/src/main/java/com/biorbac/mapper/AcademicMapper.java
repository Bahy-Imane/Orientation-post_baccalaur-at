package com.biorbac.mapper;

import com.biorbac.dto.AcademicUnitDto;
import com.biorbac.model.AcademicUnit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AcademicMapper {

    AcademicUnit toAcademicUnitDto(AcademicUnitDto academicUnitDto);
    AcademicUnitDto toAcademicUnit(AcademicUnit academicUnit);
    void updateAcademicUnitFromDto(AcademicUnitDto academicUnitDto, @MappingTarget AcademicUnit academicUnit);

}
