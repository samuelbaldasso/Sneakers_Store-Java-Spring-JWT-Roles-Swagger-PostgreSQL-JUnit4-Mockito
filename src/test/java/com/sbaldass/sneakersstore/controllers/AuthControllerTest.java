package com.sbaldass.sneakersstore.controllers;

import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.dto.LoginResponse;
import com.sbaldass.sneakersstore.dto.LoginUserDTO;
import com.sbaldass.sneakersstore.security.JWTTokenUtils;
import com.sbaldass.sneakersstore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JWTTokenUtils jwtTokenUtils;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignup() {
        User userDto = new User();
        userDto.setName("testUser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");

        when(userService.registerUser(userDto)).thenReturn(userDto);

        ResponseEntity<User> response = authController.signup(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).registerUser(userDto);
    }

    @Test
    public void testSignUpAdmin() {
        User userDto = new User();
        userDto.setName("adminUser");
        userDto.setEmail("admin@example.com");
        userDto.setPassword("adminPassword");

        when(userService.createAdministrator(userDto)).thenReturn(userDto);

        ResponseEntity<User> response = authController.signUpAdmin(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).createAdministrator(userDto);
    }

    @Test
    public void testLogin() {
        LoginUserDTO userDTO = new LoginUserDTO("samuel@gmail.com", "12345");
        User user = new User();
        user.setEmail(userDTO.getEmail());

        String token = "dummyToken";
        LoginResponse expectedJwtToken = new LoginResponse(jwtTokenUtils.getExpirationTime(), token);

        when(userService.authenticate(userDTO)).thenReturn(user);
        when(jwtTokenUtils.generateToken(user)).thenReturn(token);

        ResponseEntity<LoginResponse> response = authController.authenticate(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedJwtToken.getToken(), Objects.requireNonNull(response.getBody()).getToken());
        assertEquals(jwtTokenUtils.getExpirationTime(), response.getBody().getExpiresIn());
        verify(userService, times(1)).authenticate(userDTO);
        verify(jwtTokenUtils, times(1)).generateToken(user);
    }
}

