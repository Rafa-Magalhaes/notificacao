package com.rafael.notificacao.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println(">>> [JwtRequestFilter] Authorization Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println(">>> [JwtRequestFilter] Token recebido: " + token.substring(0, 30) + "...");

            boolean isValid = jwtUtil.validateToken(token);
            System.out.println(">>> [JwtRequestFilter] Token válido? " + isValid);

            if (isValid) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken("authenticated", null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println(">>> [JwtRequestFilter] Autenticação realizada com sucesso!");
            } else {
                System.out.println(">>> [JwtRequestFilter] Token inválido ou expirado.");
            }
        } else {
            System.out.println(">>> [JwtRequestFilter] Header Authorization ausente ou inválido.");
        }

        filterChain.doFilter(request, response);
    }
}