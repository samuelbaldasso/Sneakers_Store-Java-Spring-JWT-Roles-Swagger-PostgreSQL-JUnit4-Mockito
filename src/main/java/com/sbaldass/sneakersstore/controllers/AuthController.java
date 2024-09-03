package com.sbaldass.sneakersstore.controllers;

import com.sbaldass.sneakersstore.domain.Role;
import com.sbaldass.sneakersstore.domain.RoleName;
import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.dto.LoginResponse;
import com.sbaldass.sneakersstore.dto.LoginUserDTO;
import com.sbaldass.sneakersstore.security.JWTTokenUtils;
import com.sbaldass.sneakersstore.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authentication;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody User requestDto) {
        userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<Void> signUpAdmin(@Valid @RequestBody User requestDto) {
        userService.createAdministrator(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signup/vendor")
    public ResponseEntity<Void> signUpVendor(@Valid @RequestBody User requestDto) {
        userService.createVendor(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        authentication.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));

        String jwtToken = JWTTokenUtils.generateToken(loginUserDto.getUsername());

        LoginResponse loginResponse = new LoginResponse(JWTTokenUtils.getExpirationTime(), jwtToken);

        return ResponseEntity.ok(loginResponse);
    }
}