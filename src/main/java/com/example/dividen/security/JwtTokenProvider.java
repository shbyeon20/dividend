package com.example.dividen.security;

import com.example.dividen.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String KEY_ROLES = "roles";
    private static final Long TIME_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L;//1hour
    private final MemberService memberService;
    @Value("${spring.jwt.secret}")
    private String secretKey;

    // DAO Authentication을 진행한 후, entity의 username(principal)과 roles
    // (authorities)를 JWT에 담아내어 반환한다.
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


    public String getUsernameFromToken(String token) {
        Claims claims = this.parseClaimsFromToken(token);
        return claims.getSubject();
    }


    private Claims parseClaimsFromToken(String token) {

        SecretKey key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());


        try {
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


    // 토큰이 비어있는지 혹은 토큰이 유효기간에 부합하는지 확인
    public boolean validateToken(String token) {
        if(!StringUtils.hasText(token)) return false;

        Claims claims = this.parseClaimsFromToken(token);
        return !claims.getExpiration().before(new Date());
    }


    // spring context에 담을 Authentication을 생성함
    public Authentication getJwtAuthentication(String jwt) {
        UserDetails userDetails =
                memberService.loadUserByUsername(this.getUsernameFromToken(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails,"",
                userDetails.getAuthorities());

    }

}




