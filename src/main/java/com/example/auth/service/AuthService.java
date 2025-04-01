package com.example.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.domain.User;
import com.example.auth.dto.request.SignInRequest;
import com.example.auth.dto.response.AuthResponse;
import com.example.auth.dto.response.SignInResponse;
import com.example.auth.util.JwtTokenProvider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider){
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    public String getTestToken(String userFigureId) {
        return jwtTokenProvider.createAccessToken(userService.findByUserFigureId(userFigureId));
    }

    public SignInResponse signIn(HttpServletResponse response, SignInRequest request) {
        User user = userService.findByUserFigureId(request.userFigureId());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        response.setHeader("Authorization", accessToken);

        return SignInResponse.of(user.getName());
    }

    public AuthResponse validateToken(String token) {
       // String parseToken = resolveToken(token);

        return AuthResponse.from(jwtTokenProvider.validateToken(token));
    }

    private String resolveToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        throw new RuntimeException("Invalid token");
    }
}
