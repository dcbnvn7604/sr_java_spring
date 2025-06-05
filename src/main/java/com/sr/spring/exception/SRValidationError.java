package com.sr.spring.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SRValidationError extends RuntimeException{
    private String field;
    private String message;
}
