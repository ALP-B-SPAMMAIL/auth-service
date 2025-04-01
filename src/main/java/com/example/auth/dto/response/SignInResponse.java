package com.example.auth.dto.response;

public record SignInResponse(
        String name
) {
    public static SignInResponse of(String name) {
        return new SignInResponse(name);
    }
}
