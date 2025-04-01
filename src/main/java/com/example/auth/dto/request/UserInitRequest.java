package com.example.auth.dto.request;

public record UserInitRequest(
        String userFigureId,
        String password,
        String name,
        String job,
        String gender,
        String birthDate,
        String interest
) {
}
