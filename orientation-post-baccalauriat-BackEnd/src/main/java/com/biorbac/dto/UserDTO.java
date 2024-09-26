package com.biorbac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserDTO {

    private String userName;
    private String password;
    private String email;

}