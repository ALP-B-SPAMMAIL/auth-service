package com.example.auth.dto.response;

public record NewSignInResponse(
        String name,
        String accessToken,
        int userId
) {
    public static NewSignInResponse of(String name, String accessToken, int userId) {
        return new NewSignInResponse(name, accessToken, userId);
    }
}
