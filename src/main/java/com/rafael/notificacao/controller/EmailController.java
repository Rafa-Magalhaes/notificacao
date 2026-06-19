package com.rafael.notificacao.controller;

import com.rafael.notificacao.business.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    /**
     * Endpoint para enviar e-mail de boas-vindas usando template Thymeleaf
     */
    @PostMapping("/enviar-boas-vindas")
    public ResponseEntity<String> enviarEmailBoasVindas(
            @RequestParam String destinatario,
            @RequestParam String nome) {

        // Monta o mapa de variáveis que serão usadas no template
        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("nome", nome);

        // Chama o Service passando os dados
        emailService.enviarEmailComTemplate(
                destinatario,
                "Bem-vindo à nossa plataforma!",
                "email-boas-vindas",
                variaveis
        );

        return ResponseEntity.ok("E-mail enviado com sucesso para: " + destinatario);
    }
}