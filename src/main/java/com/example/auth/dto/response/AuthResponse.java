package com.example.auth.dto.response;

public record AuthResponse(
        String userFigureId
) {

    public static AuthResponse from(String userFigureId) {
        return new AuthResponse(userFigureId);
    }
}
