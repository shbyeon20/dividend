package com.example.dividen.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final String KEY_ROLES = "roles";
    private static final Long TIME_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L;//1hour

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        var now = new Date();
        var expiredTimes = new Date(now.getTime() + TIME_TOKEN_EXPIRE_TIME);

        SecretKey key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredTimes)
                .signWith(key, SignatureAlgorithm.HS512)  // Using SecretKeySpec instead of a raw string
                .compact();
    }
}
