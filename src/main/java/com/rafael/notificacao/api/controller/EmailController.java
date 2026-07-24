package com.rafael.notificacao.api.controller;

import com.rafael.notificacao.api.dto.BffNotificacaoMailRequestDTO;
import com.rafael.notificacao.api.dto.NotificacaoBffMailResponseDTO;
import com.rafael.notificacao.domain.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
@Tag(name = "Notificações", description = "Endpoints para processamento e disparo automatizado de e-mails via Thymeleaf e SMTP")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/enviar-tarefa")
    @Operation(
            summary = "Envia e-mail de lembrete de tarefa",
            description = "Endpoint interno protegido por Token de Serviço. Processa o template HTML dinâmico e dispara o e-mail via servidor SMTP."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail disparado com sucesso (Retorna status ENVIADO)"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos (Bean Validation)"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Token de serviço ausente ou inválido"),
            @ApiResponse(responseCode = "500", description = "Falha no servidor SMTP ou erro de envio (Retorna status FALHOU para retentativa)")
    })
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