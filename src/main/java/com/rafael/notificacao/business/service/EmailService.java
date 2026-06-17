package com.rafael.notificacao.business.service;

import com.rafael.notificacao.infrastructure.mail.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailSender emailSender;

    public void enviarEmailSimples(String destinatario, String assunto, String corpoHtml) {

        emailSender.sendEmail(destinatario, assunto, corpoHtml);
    }
}