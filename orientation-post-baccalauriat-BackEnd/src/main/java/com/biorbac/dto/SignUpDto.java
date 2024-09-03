package com.biorbac.dto;


import com.biorbac.enums.Specialization;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    private String userName;
    private String email;
    private String password;
    private String grade;
    private String graduationYear;
    private Specialization specialization;

}

