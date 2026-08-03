package com.rafael.notificacao.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret:chave-falsa-para-testes-no-ci-nao-usar-em-producao}")
    private String secretKey;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractTokenType(String token) {
        Claims claims = extractClaims(token);
        String type = claims.get("tokentype", String.class);
        if (type == null) {
            type = claims.get("tokenType", String.class);
        }
        return type;
    }

    public boolean isServiceToken(String token) {
        try {
            return "SERVICE".equals(extractTokenType(token));
        } catch (Exception e) {
            return false;
        }
    }
}