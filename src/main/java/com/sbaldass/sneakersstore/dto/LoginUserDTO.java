package com.sbaldass.sneakersstore.dto;

import lombok.Data;

@Data
public class LoginUserDTO {
    private String email;
    private String password;

    public LoginUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
