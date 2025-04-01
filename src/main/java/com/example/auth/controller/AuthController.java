package com.example.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dto.request.RegisterRequest;
import com.example.auth.dto.request.SignInRequest;
import com.example.auth.dto.response.AuthResponse;
import com.example.auth.dto.response.SignInResponse;
import com.example.auth.service.AuthService;
import com.example.auth.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }

    //@Operation(summary = "테스트용 토큰발급", description = "테스트용 토큰발급")
    // @GetMapping("/test/{userId}")
    // public String testToken(@PathVariable Long userId) {
    //     return authService.getTestToken(userId);
    // }

    @PostMapping("/register")
    public ResponseEntity<String> postMethodName(@RequestBody RegisterRequest registerRequest) {
        String message = userService.register(registerRequest);
        
        return ResponseEntity.ok(message);
    }
    

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody SignInRequest signInRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.signIn(response, signInRequest));
    }

    @GetMapping("/validate-token")
    public AuthResponse validateToken(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token is missing or malformed");
        }

        String accessToken = token.substring(7);  // Remove "Bearer " prefix
        System.out.println(accessToken);

        AuthResponse authResponse = authService.validateToken(accessToken);
        if (authResponse == null) {
            throw new InvalidTokenException("Invalid or expired token");
        }

        return authResponse;
    }
    
    public class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }




    // //@Operation(summary = "로그인 진행", description = "access token은 header에 Authorization: Bearer 로, refresh token은 쿠키로 전달")
    // @PostMapping("/sign-in")
    // public ResponseEntity<ResponseTemplate<?>> signIn(
    //         @RequestBody SignInRequest signInRequest, HttpServletResponse response
    // ) {
    //     SignInResponse selfSignInResponse = authService.signIn(response, signInRequest);

    //     return ResponseEntity
    //             .status(HttpStatus.OK).body();
    //             //.body(ResponseTemplate.from(selfSignInResponse));
    // }

    // //@Operation(summary = "토큰 유효성 검사", description = "프론트 사용 X - 백엔드 통신")
    // @GetMapping("/validate-token")
    // public AuthResponse validateToken(@RequestHeader("Authorization") String token) {
    //     AuthResponse userId = authService.validateToken(token);

    //     //log.info("userId: {}", userId.userId());

    //     return userId;
    // }
}