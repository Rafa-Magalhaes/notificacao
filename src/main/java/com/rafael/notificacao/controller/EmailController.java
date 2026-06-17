package com.rafael.notificacao.controller;

import com.rafael.notificacao.business.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarEmail(
            @RequestParam String destinatario,
            @RequestParam String assunto,
            @RequestBody String corpoHtml) {

        emailService.enviarEmailSimples(destinatario, assunto, corpoHtml);

        return ResponseEntity.ok("E-mail enviado com sucesso para: " + destinatario);
    }
}