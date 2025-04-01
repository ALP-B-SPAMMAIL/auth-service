package com.example.auth.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.auth.domain.User;
import com.example.auth.service.type.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private Key key;
    private static final String USER_ROLE = "role";

    @Autowired
    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }


    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(User user) {
        return createToken(user, jwtProperties.accessTokenExpiration());
    }

    /**
     * JWT 토큰 생성 메소드
     */
    private String createToken(User user, long expiration) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + expiration);

        return Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .setIssuer(jwtProperties.issuer())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(user.getUserFigureId())
                .compact();
    }

    public String validateToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        String userId = claims.getBody().getSubject();

        //log.info("login user: {}", userId);

        if (!claims.getBody().getExpiration().after(new Date())) {
            return "0";
        }

        return userId;
    }

    public String getUserId(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return claims.getBody().getSubject();
    }

    // public String getUserRole(String token) {
    //     Jws<Claims> claims = Jwts.parserBuilder()
    //             .setSigningKey(key)
    //             .build()
    //             .parseClaimsJws(token);

    //     return claims.getBody().get(USER_ROLE, String.class);
    // }
}