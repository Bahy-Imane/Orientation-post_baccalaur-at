package com.biorbac.dto;

import com.biorbac.enums.InstitutionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstitutionDto {

    private String institutionName;
    private String description;
    private String address;
    private String website;
    private InstitutionType institutionType;

}
