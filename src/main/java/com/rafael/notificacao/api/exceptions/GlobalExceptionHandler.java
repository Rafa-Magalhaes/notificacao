package com.rafael.notificacao.api.exceptions;

import com.rafael.notificacao.api.dto.NotificacaoBffMailResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j // ← Anotação para usarmos o log.error
@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Intercepta especificamente o erro de e-mail
    @ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<NotificacaoBffMailResponseDTO> handleEmailNotSentException(EmailNotSentException ex) {

        // Logamos o erro no terminal para podermos investigar depois
        log.error("Falha no disparo de e-mail interceptada: {}", ex.getMessage(), ex);

        // Devolvemos o DTO com "FALHOU" para o BFF orquestrar a retentativa
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new NotificacaoBffMailResponseDTO("FALHOU"));
    }

    // 2. Erros de Validação e Genéricos (Podem devolver apenas as Strings como você fez)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requisição inválida: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Erro interno não tratado: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno no servidor");
    }
}