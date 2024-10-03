package com.biorbac.controller;

import com.biorbac.dto.JwtAuthResponse;
import com.biorbac.dto.LoginDto;
import com.biorbac.dto.SignUpDto;
import com.biorbac.model.Institution;
import com.biorbac.service.AuthService;
import com.biorbac.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    private  AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponse response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody SignUpDto signUpDto) {
        String response = authService.signUp(signUpDto);

        return ResponseEntity.ok(response);
    }
}
