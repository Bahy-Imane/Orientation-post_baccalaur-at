package com.biorbac.dto;

import com.biorbac.model.Institution;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldOfStudyDto {

    private Long fosId;
    private String name;
    private String bacTypeRequired;
    private double minimumBacNote;
    private String departmentName;
    private String institutionName;
    private String fieldOfStudyLogo;
    private Long institutionId;


}
