package com.rafael.notificacao.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Ignora checagem de token para o Swagger e Actuator
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/actuator")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token) && jwtUtil.isServiceToken(token)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken("service-authenticated", null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info(">>> [Notificação] Service Token validado com sucesso para a rota: {}", path);
            } else {
                log.warn(">>> [Notificação] Tentativa de acesso negada: Token inválido, expirado ou não é um Service Token.");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado: Requer Token de Serviço válido.");
                return;
            }
        } else {
            log.warn(">>> [Notificação] Header Authorization ausente na tentativa de acesso à rota: {}", path);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token ausente ou formato inválido.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}