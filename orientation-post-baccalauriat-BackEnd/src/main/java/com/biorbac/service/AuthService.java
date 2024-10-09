package com.biorbac.service;

import com.biorbac.dto.JwtAuthResponse;
import com.biorbac.dto.LoginDto;
import com.biorbac.dto.SignUpDto;
import com.biorbac.enums.Role;
import com.biorbac.jwt.JwtTokenProvider;
import com.biorbac.model.FieldOfStudy;
import com.biorbac.model.Student;
import com.biorbac.model.User;
import com.biorbac.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final FieldOfStudyService fieldOfStudyService;

    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserNameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        User user = userRepository.findByUserNameOrEmail(loginDto.getUserNameOrEmail(), loginDto.getUserNameOrEmail());
        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        response.setTokenType("Bearer");
        response.setUserName(user.getUsername());
        response.setRole(String.valueOf(user.getRole()));
        response.setUserId(user.getUserId());

//        if (user instanceof Student) {
//            List<FieldOfStudy> recommendations = fieldOfStudyService.recommendBasedOnStudent((Student) user);
//            response.setRecommendations(recommendations);
//        }

        return response;
    }


    public String signUp(SignUpDto signUpDto) {
        if (userRepository.existsByUserName(signUpDto.getUserName())) {
            throw new RuntimeException("Username is already taken!");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }
        Student user = new Student();
        user.setUserName(signUpDto.getUserName());
        user.setEmail(signUpDto.getEmail());
        user.setRole(Role.STUDENT);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setBacScore(signUpDto.getBacScore());
        user.setLocation(signUpDto.getLocation());
        user.setBacType(signUpDto.getBacType());
        user.setDateOfBirth(signUpDto.getDateOfBirth());
        userRepository.save(user);

        return "User registered successfully!";
    }

}

