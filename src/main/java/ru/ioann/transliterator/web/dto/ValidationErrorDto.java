package ru.ioann.transliterator.web.dto;

import org.springframework.http.HttpStatus;

public final class ValidationErrorDto {
    public HttpStatus status;
    public String errorMessage;
}
