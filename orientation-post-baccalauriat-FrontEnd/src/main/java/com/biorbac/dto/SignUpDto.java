package com.biorbac.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    private String userName;
    private String email;
    private String password;
    private String major;
    private String graduationYear;

}

