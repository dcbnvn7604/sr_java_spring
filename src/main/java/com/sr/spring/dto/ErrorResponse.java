package com.sr.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse<T> {
    private String message;
    private T details;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Field {
        private String field;
        private String message;
    }
}
