package com.rafael.notificacao.api.exceptions;

public class EmailNotSentException extends RuntimeException {
    public EmailNotSentException(String message, Throwable cause) {
        super(message, cause);
    }
}