package com.sr.spring.dto;

import lombok.*;

import java.util.Map;


@AllArgsConstructor
@Getter
public class ErrorResponse {
    @NonNull
    private String message;
    private Map<String, String> detail;
}
