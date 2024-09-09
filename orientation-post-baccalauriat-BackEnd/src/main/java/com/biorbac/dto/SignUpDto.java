package com.biorbac.dto;

import com.biorbac.enums.BacType;
import com.biorbac.enums.Interest;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    private String userName;
    private String email;
    private String password;
    private double bacScore;
    private String location;
    private BacType bacType;
    private Interest interest;

}

