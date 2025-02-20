package com.mustycodified.fintech_qrpayment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class CustomValidationException extends RuntimeException{
    public CustomValidationException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
    }

    private final HttpStatus status;
}
