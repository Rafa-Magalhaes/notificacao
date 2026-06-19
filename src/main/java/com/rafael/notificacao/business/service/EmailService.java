package com.rafael.notificacao.business.service;

import com.rafael.notificacao.infrastructure.mail.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * Envia um e-mail utilizando um template Thymeleaf
     *
     * @param destinatario   E-mail de quem vai receber
     * @param assunto        Assunto do e-mail
     * @param nomeDoTemplate Nome do arquivo do template (sem extensão .html)
     * @param variaveis      Mapa com as variáveis que serão usadas no template
     */
    public void enviarEmailComTemplate(String destinatario,
                                       String assunto,
                                       String nomeDoTemplate,
                                       Map<String, Object> variaveis) {

        // 1. Criamos um contexto do Thymeleaf
        Context context = new Context();

        // 2. Passamos as variáveis para o contexto
        context.setVariables(variaveis);

        // 3. Processamos o template HTML com as variáveis
        String htmlContent = templateEngine.process("email/" + nomeDoTemplate, context);

        // 4. Enviamos o e-mail já com o HTML processado
        emailSender.sendEmail(destinatario, assunto, htmlContent);
    }
}