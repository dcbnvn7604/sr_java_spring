package com.sr.spring.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.sr.spring.dto.ErrorResponse;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class SRExceptionHandler {
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse<List<ErrorResponse.Field>>>> handleValidationException(WebExchangeBindException ex) {
        ErrorResponse<List<ErrorResponse.Field>> errorResponse = new ErrorResponse<>();
        List<ErrorResponse.Field> fieldErrors = ex.getFieldErrors().stream()
                .map(fieldError -> {
                    ErrorResponse.Field field = new ErrorResponse.Field();
                    field.setField(fieldError.getField());
                    field.setMessage(fieldError.getDefaultMessage());
                    return field;
                })
                .toList();
        errorResponse.setDetails(fieldErrors);
        errorResponse.setMessage("Validation failed");

        return Mono.just(ResponseEntity.badRequest()
                .body(errorResponse));
    }

    @ExceptionHandler(SRValidationError.class)
    public Mono<ResponseEntity<ErrorResponse<List<ErrorResponse.Field>>>> handleSRValidationError(SRValidationError ex) {
        ErrorResponse<List<ErrorResponse.Field>> errorResponse = new ErrorResponse<>();
        ErrorResponse.Field field = new ErrorResponse.Field();
        field.setField(ex.getField());
        field.setMessage(ex.getMessage());
        errorResponse.setDetails(List.of(field));
        errorResponse.setMessage("Validation failed");

        return Mono.just(ResponseEntity.badRequest()
                .body(errorResponse));
    }
}
