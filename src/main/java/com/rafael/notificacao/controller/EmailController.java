package com.rafael.notificacao.controller;

import com.rafael.notificacao.business.dto.EmailEnvioRequestDTO;
import com.rafael.notificacao.business.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/enviar-boas-vindas")
    public ResponseEntity<String> enviarEmailBoasVindas(
            @Valid @RequestBody EmailEnvioRequestDTO request) {

        emailService.enviarEmailComTemplate(
                request.getDestinatario(),
                "Bem-vindo à nossa plataforma!",
                "email-boas-vindas",
                java.util.Map.of("nome", request.getNome())
        );

        return ResponseEntity.ok("E-mail enviado com sucesso para: " + request.getDestinatario());
    }
}