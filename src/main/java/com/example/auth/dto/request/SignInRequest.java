package com.example.auth.dto.request;

public record SignInRequest(
        String userFigureId,
        String password
) {
}
