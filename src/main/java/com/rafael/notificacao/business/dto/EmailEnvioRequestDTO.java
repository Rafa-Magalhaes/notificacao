package com.rafael.notificacao.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailEnvioRequestDTO {

    @NotBlank(message = "O destinatário é obrigatório")
    @Email(message = "E-mail do destinatário inválido")
    private String destinatario;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;
}