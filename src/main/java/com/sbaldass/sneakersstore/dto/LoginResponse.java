package com.sbaldass.sneakersstore.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;

    public LoginResponse(long expiresIn, String token) {
        this.token = getToken();
        this.expiresIn = getExpiresIn();
    }
}
