package com.biorbac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldOfStudyDto {

    private String name;
    private String bacTypeRequired;
    private double minimumBacNote;
    private String departmentName;
}
