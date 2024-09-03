package com.biorbac.dto;

import com.biorbac.enums.InstitutionType;
import com.biorbac.enums.Specialization;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstitutionDto {
    private String name;
    private String subject;
    private String ville;
    private String website;
    private String description;
    private String conditionsAdmission;
    private String emailContact;
    private Specialization specialization;
    private InstitutionType institutionType;
}
