package com.rafael.notificacao.api.controller;

import com.rafael.notificacao.api.dto.BffNotificacaoMailRequestDTO;
import com.rafael.notificacao.api.dto.NotificacaoBffMailResponseDTO;
import com.rafael.notificacao.domain.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/enviar-tarefa")
    public ResponseEntity<NotificacaoBffMailResponseDTO> enviarNotificacaoTarefa(
            @Valid @RequestBody BffNotificacaoMailRequestDTO request) {

        emailService.enviarEmailComTemplate(
                request.getEmail(),
                "Lembrete: " + request.getTituloTarefa(),
                "email-tarefa-agendada",
                Map.of(
                        "nome", request.getNome(),
                        "titulo", request.getTituloTarefa(),
                        "descricao", request.getDescricaoTarefa() != null ? request.getDescricaoTarefa() : "",
                        "dataHora", request.getDataHoraAgendada()
                )
        );

        // Retorna o JSON padronizado de sucesso
        return ResponseEntity.ok(new NotificacaoBffMailResponseDTO("ENVIADO"));
    }
}