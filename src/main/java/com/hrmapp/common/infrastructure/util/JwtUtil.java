package com.hrmapp.common.infrastructure.util;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.port.input.UserApplicationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final UserApplicationService userApplicationService;

    private static final String SECRET = "pYXQiOjEMsnVz5AYXJvbXMNjc2OTgyNTUzfQ";

    public JwtUtil(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    public String generateJWTToken(UserDto userDto){
        return Jwts.builder()
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setIssuer("HRM-app")
                .claim("userId", userDto.id())
                .claim("username", userDto.username())
                .setExpiration(Date.from(ZonedDateTime.now().plusHours(1).toInstant()))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validateJWTToken(String token){
        return getUsername(token) != null && isExpired(token);
    }

    private boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.get("username").toString();
    }
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
    public UUID getUserIdFromJWTToken(String token){
        return UUID.fromString(getClaims(token).get("userId").toString());
    }
    public Instant getTokenExpiry(String jwtToken) {
        Claims claims = getClaims(jwtToken);
        return claims.getExpiration().toInstant();
    }
    public boolean isTokenRevoked(String token){
        var session = userApplicationService.findSessionByToken(token);
        return session.getActive();
    }
}
