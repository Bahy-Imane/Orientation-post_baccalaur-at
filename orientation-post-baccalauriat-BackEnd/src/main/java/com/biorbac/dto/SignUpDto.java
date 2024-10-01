package com.biorbac.dto;

import com.biorbac.enums.BacType;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto extends UserDTO{
    
    private LocalDate dateOfBirth;
    private double bacScore;
    private String location;
    private BacType bacType;

}

