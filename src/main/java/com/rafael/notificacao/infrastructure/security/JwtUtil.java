package com.rafael.notificacao.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@SuppressWarnings("deprecation")
@Component
public class JwtUtil {

    @Value("${jwt.secret:chave-falsa-para-testes-no-ci-nao-usar-em-producao}")
    private String secretKey;

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
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
        return extractClaims(token).get("tokenType", String.class);
    }

    public boolean isServiceToken(String token) {
        try {
            return "SERVICE".equals(extractTokenType(token));
        } catch (Exception e) {
            return false;
        }
    }
}