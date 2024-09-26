package com.biorbac.mapper;

import com.biorbac.dto.DepartmentDto;
import com.biorbac.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {

    Department toDepartmentDto(DepartmentDto departmentDto);
    DepartmentDto toDepartment(Department department);
    void updateDepartmentFromDto(DepartmentDto departmentDto, @MappingTarget Department department);
}
