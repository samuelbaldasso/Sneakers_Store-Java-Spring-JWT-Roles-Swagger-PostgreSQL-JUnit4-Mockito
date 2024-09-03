package com.sbaldass.sneakersstore.dto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@Data
public class ApiErrorResponse {
    private int response;
    private String message;

    public ApiErrorResponse(int response, String message) {
        this.response = response;
        this.message = message;
    }
}
