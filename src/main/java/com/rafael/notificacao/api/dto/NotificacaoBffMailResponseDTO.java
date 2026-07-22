package com.rafael.notificacao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoBffMailResponseDTO {
    private String status; // Apenas "ENVIADO" ou "FALHOU"
}