package com.biorbac.dto;

import com.biorbac.model.FieldOfStudy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String userName;
    private String role;
    private Long userId;
    private List<FieldOfStudy> recommendations;
}