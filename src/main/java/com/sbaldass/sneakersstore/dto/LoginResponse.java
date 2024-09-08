package com.sbaldass.sneakersstore.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Long expiresIn;

    public LoginResponse(long expirationTime, String jwtToken) {
        this.expiresIn = expirationTime;
        this.token = jwtToken;
    }
}
