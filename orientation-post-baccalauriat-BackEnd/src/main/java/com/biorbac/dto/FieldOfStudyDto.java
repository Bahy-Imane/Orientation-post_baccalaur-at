package com.biorbac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldOfStudyDto {

    private String name;
    private String description;
    private Double entryRequirement;
    private Integer numberOfStudents;


}
