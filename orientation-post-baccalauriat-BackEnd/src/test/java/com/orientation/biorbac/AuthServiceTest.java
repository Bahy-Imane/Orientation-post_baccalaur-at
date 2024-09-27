package com.orientation.biorbac;

import com.biorbac.dto.JwtAuthResponse;
import com.biorbac.dto.LoginDto;
import com.biorbac.dto.SignUpDto;
import com.biorbac.enums.BacType;
import com.biorbac.enums.Interest;
import com.biorbac.enums.Role;
import com.biorbac.jwt.JwtTokenProvider;
import com.biorbac.model.Student;
import com.biorbac.model.User;
import com.biorbac.repository.UserRepository;
import com.biorbac.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setUserNameOrEmail("testUser");
        loginDto.setPassword("testPassword");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        User user = new User();
        user.setUserName("testUser");
        user.setRole(Role.STUDENT);
        user.setUserId(1L);
        when(userRepository.findByUserNameOrEmail("testUser", "testUser")).thenReturn(user);

        String token = "fakeJwtToken";
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(token);

        // Act
        JwtAuthResponse response = authService.login(loginDto);

        // Assert
        assertNotNull(response);
        assertEquals("Bearer", response.getTokenType());
        assertEquals(token, response.getAccessToken());
        assertEquals("testUser", response.getUserName());
        assertEquals("STUDENT", response.getRole());
        assertEquals(1L, response.getUserId());

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByUserNameOrEmail("testUser", "testUser");
        verify(jwtTokenProvider, times(1)).generateToken(authentication);
    }

    @Test
    void testSignUp_Success() {
        // Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUserName("newUser");
        signUpDto.setEmail("newUser@example.com");
        signUpDto.setPassword("password123");
        signUpDto.setBacScore(85);
        signUpDto.setLocation("Paris");
        signUpDto.setBacType(BacType.SCIENTIFIC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        signUpDto.setDateOfBirth(LocalDate.parse("2000-01-01", formatter));
        signUpDto.setInterest(Interest.ENGINEERING);


        when(userRepository.existsByUserName(signUpDto.getUserName())).thenReturn(false);
        when(userRepository.existsByEmail(signUpDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signUpDto.getPassword())).thenReturn("encodedPassword");

        // Act
        String result = authService.signUp(signUpDto);

        // Assert
        assertEquals("User registered successfully!", result);
        verify(userRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testSignUp_UsernameTaken() {
        // Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUserName("existingUser");
        when(userRepository.existsByUserName(signUpDto.getUserName())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.signUp(signUpDto);
        });
        assertEquals("Username is already taken!", exception.getMessage());
    }

    @Test
    void testSignUp_EmailTaken() {
        // Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUserName("newUser");
        signUpDto.setEmail("existingEmail@example.com");
        when(userRepository.existsByUserName(signUpDto.getUserName())).thenReturn(false);
        when(userRepository.existsByEmail(signUpDto.getEmail())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.signUp(signUpDto);
        });
        assertEquals("Email is already taken!", exception.getMessage());
    }
}
